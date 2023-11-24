package diplom.gorinych.ui.presentation.login_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.repository.HouseRepository
import diplom.gorinych.domain.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: HouseRepository
) : ViewModel() {
    private val _state = MutableStateFlow(LoginScreenState())
    val state = _state.asStateFlow()


    fun onEvent(loginEvent: LoginEvent) {
        when (loginEvent) {
            LoginEvent.OnLogin -> {
                viewModelScope.launch {
                    val result = repository.login(
                        login = _state.value.login,
                        password = _state.value.password
                    )
                    when (result) {
                        is Resource.Error -> {
                            _state.value.copy(
                                message = result.message
                            )
                                .updateStateUI()
                        }

                        is Resource.Success -> {
                            if (result.data == null) {
                                _state.value.copy(
                                    message = "login or password invalid"
                                )
                                    .updateStateUI()
                            } else {
                                if (!result.data.isBlocked) {
                                    _state.value.copy(
                                        message = "success"
                                    )
                                        .updateStateUI()
                                } else {
                                    _state.value.copy(
                                        message = "user ${result.data.name} is blocked"
                                    )
                                        .updateStateUI()
                                }
                            }
                        }
                    }
                }
            }

            is LoginEvent.SetLogin -> {
                _state.value.copy(
                    login = loginEvent.login
                )
                    .updateStateUI()
            }

            is LoginEvent.SetPassword -> {
                _state.value.copy(
                    password = loginEvent.password
                )
                    .updateStateUI()
            }
        }
    }


    private fun LoginScreenState.updateStateUI() {
        _state.update {
            this
        }
    }

}