package diplom.gorinych.ui.presentation.registration_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.repository.HouseRepository
import diplom.gorinych.domain.utils.ALREADY_EXIST
import diplom.gorinych.domain.utils.ROLE_USER
import diplom.gorinych.domain.utils.Resource
import diplom.gorinych.domain.utils.SUCCESS_REGISTRATION
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: HouseRepository
) : ViewModel() {
    private val _state = MutableStateFlow(RegistrationScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            when (val result = repository.getAllUsers()) {
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