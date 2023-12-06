package diplom.gorinych.ui.presentation.registration_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.repository.HouseRepository
import diplom.gorinych.domain.repository.MailRepository
import diplom.gorinych.domain.utils.ALREADY_EXIST
import diplom.gorinych.domain.utils.ROLE_USER
import diplom.gorinych.domain.utils.Resource
import diplom.gorinych.domain.utils.SUCCESS_REGISTRATION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: HouseRepository,
    private val mailRepository: MailRepository
) : ViewModel() {
    private val _state = MutableStateFlow(RegistrationScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val result = repository.getAllUsers()
            result.collect {
                when (it) {
                    is Resource.Error -> {
                        _state.value.copy(
                            message = it.message
                        )
                            .updateStateUI()
                    }

                    is Resource.Success -> {
                        _state.value.copy(
                            users = it.data ?: emptyList()
                        )
                            .updateStateUI()
                    }
                }
            }
        }
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
                        repository.insertUser(
                            name = registrationEvent.name,
                            password = registrationEvent.password,
                            phone = registrationEvent.phone,
                            email = registrationEvent.email,
                            role = ROLE_USER,
                            isBlocked = false
                        )
                    }
                    viewModelScope.launch (Dispatchers.IO){
                        mailRepository.sendEmail(
                            login = "edurda77@gmail.com",
                            password = "Khayarov1977!",
                            email = registrationEvent.email,
                            theme = SUCCESS_REGISTRATION,
                            content = "${registrationEvent.name}\n${registrationEvent.password}\n${registrationEvent.phone}\n${registrationEvent.email}"
                        )
                    }
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