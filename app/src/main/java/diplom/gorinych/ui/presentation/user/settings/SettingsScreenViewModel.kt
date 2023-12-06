package diplom.gorinych.ui.presentation.user.settings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.repository.HouseRepository
import diplom.gorinych.domain.repository.MailRepository
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
    private val repository: HouseRepository,
    private val savedStateHandle: SavedStateHandle,
    private val mailRepository: MailRepository
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
                    repository.addCall(
                        name = _state.value.user?.name ?: "",
                        phone = _state.value.user?.phone ?: ""
                    )
                }
            }

            is SettingsEvent.OnChangePassword -> {
                if (event.oldPassword != _state.value.user?.password) {
                    _state.value.copy(
                        message = OLD_PASSWORD_INCORRECT
                    )
                        .updateStateUI()
                } else {
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
                            _state.value.user?.copy(password = event.password)
                                ?.let { repository.updateUser(it) }
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
            }
        }
    }

    private suspend fun loadUserData(userId: Int) {
        when (val result = repository.getUserById(userId)) {
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