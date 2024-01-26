package diplom.gorinych.ui.presentation.registration_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.repository.MailRepository
import diplom.gorinych.domain.repository.RemoteRepository
import diplom.gorinych.domain.utils.ALREADY_EXIST
import diplom.gorinych.domain.utils.EMAIL_LOGIN
import diplom.gorinych.domain.utils.EMAIL_PASSWORD
import diplom.gorinych.domain.utils.ROLE_USER
import diplom.gorinych.domain.utils.Resource
import diplom.gorinych.domain.utils.SUCCESS_REGISTRATION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val mailRepository: MailRepository
) : ViewModel() {
    private val _state = MutableStateFlow(RegistrationScreenState())
    val state = _state.asStateFlow()

    init {
        getUsers()
    }

    fun onEvent(registrationEvent: RegistrationEvent) {
        when (registrationEvent) {
            is RegistrationEvent.OnRegistrationUser -> {
                if (_state.value.users.any { it.name == registrationEvent.name }) {
                    _state.value.copy(
                        message = ALREADY_EXIST
                    )
                        .updateStateUI()
                } else {
                    _state.value.copy(
                        message = SUCCESS_REGISTRATION
                    )
                        .updateStateUI()
                    viewModelScope.launch {
                        remoteRepository.addNewUser(
                            name = registrationEvent.name,
                            password = registrationEvent.password,
                            phone = registrationEvent.phone,
                            email = registrationEvent.email,
                            role = ROLE_USER,
                            isBlocked = false
                        )
                        delay(100)
                        getUsers()
                    }
                    viewModelScope.launch(Dispatchers.IO) {
                        mailRepository.sendEmail(
                            login = EMAIL_LOGIN,
                            password = EMAIL_PASSWORD,
                            email = registrationEvent.email,
                            theme = SUCCESS_REGISTRATION,
                            content = "${registrationEvent.name}\n${registrationEvent.password}\n${registrationEvent.phone}\n${registrationEvent.email}"
                        )
                    }
                }
            }
        }
    }

    private fun getUsers() {
        viewModelScope.launch {
            when (val result = remoteRepository.getAllUsers()) {
                is Resource.Error -> {
                    _state.value.copy(
                        message = result.message
                    )
                        .updateStateUI()
                }

                is Resource.Success -> {
                    _state.value.copy(
                        users = result.data ?: emptyList()
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private fun RegistrationScreenState.updateStateUI() {
        _state.update {
            this
        }
    }
}