package diplom.gorinych.ui.presentation.user.settings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.repository.MailRepository
import diplom.gorinych.domain.repository.RemoteRepository
import diplom.gorinych.domain.repository.SharedRepository
import diplom.gorinych.domain.utils.EMAIL_LOGIN
import diplom.gorinych.domain.utils.EMAIL_PASSWORD
import diplom.gorinych.domain.utils.OLD_PASSWORD_INCORRECT
import diplom.gorinych.domain.utils.PASSWORD_DONT_MATCH
import diplom.gorinych.domain.utils.PASSWORD_IS_CHANGED
import diplom.gorinych.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val savedStateHandle: SavedStateHandle,
    private val mailRepository: MailRepository,
    private val sharedRepository: SharedRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val userId = savedStateHandle.get<Int>("idUser") ?: return@launch
            async { loadUserData(userId) }.onAwait
        }
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            SettingsEvent.OnSendCall -> {
                viewModelScope.launch {
                    remoteRepository.addNewCall(
                        name = _state.value.user?.name ?: "",
                        phone = _state.value.user?.phone ?: "",
                        isResponse = false
                    )
                }
            }

            is SettingsEvent.OnChangePassword -> {
                if (event.password != event.repeatPassword) {
                    _state.value.copy(
                        message = PASSWORD_DONT_MATCH
                    )
                        .updateStateUI()
                } else {
                    viewModelScope.launch {
                        _state.value.copy(
                            message = PASSWORD_IS_CHANGED,
                        )
                            .updateStateUI()
                        _state.value.user?.let {
                            remoteRepository.changePassword(
                                idUser = it.id,
                                oldPassword = event.oldPassword,
                                newPassword = event.password
                            )
                        }
                        viewModelScope.launch(Dispatchers.IO) {
                            mailRepository.sendEmail(
                                login = EMAIL_LOGIN,
                                password = EMAIL_PASSWORD,
                                email = _state.value.user?.email ?: "",
                                theme = PASSWORD_IS_CHANGED,
                                content = event.password
                            )
                        }
                        delay(2000)
                        _state.value.copy(
                            message = "",
                        )
                            .updateStateUI()
                    }
                }
            }

            SettingsEvent.Exit -> {
                sharedRepository.setUser(-1)
                sharedRepository.setRole("")
            }
        }
    }

    private suspend fun loadUserData(userId: Int) {
        when (val result = remoteRepository.getUserBiId(userId)) {
            is Resource.Error -> {
                _state.value.copy(
                    message = result.message
                )
                    .updateStateUI()
            }

            is Resource.Success -> {
                _state.value.copy(
                    user = result.data
                )
                    .updateStateUI()
            }
        }
    }

    private fun SettingsScreenState.updateStateUI() {
        _state.update {
            this
        }
    }
}