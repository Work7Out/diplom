package diplom.gorinych.ui.presentation.login_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.repository.HouseRepository
import diplom.gorinych.domain.repository.RemoteRepository
import diplom.gorinych.domain.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.delay

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: HouseRepository,
    private val remoteRepository: RemoteRepository
) : ViewModel() {
    private val _state = MutableStateFlow(LoginScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            _state.value.copy(
                loginScreenWindowState = LoginScreenWindowState.BaseScreen
            )
                .updateStateUI()
        }
    }

    fun onEvent(loginEvent: LoginEvent) {
        when (loginEvent) {
            LoginEvent.OnLogin -> {
                viewModelScope.launch {
                    val remoteResult = remoteRepository.getLogin(
                        login = _state.value.login,
                        password = _state.value.password
                    )
                    when (remoteResult) {
                        is Resource.Error -> {
                            _state.value.copy(
                                message = remoteResult.message
                            )
                                .updateStateUI()
                        }

                        is Resource.Success -> {
                            if (remoteResult.data == null) {
                                _state.value.copy(
                                    message = "login or password invalid"
                                )
                                    .updateStateUI()
                            } else {
                                if (!remoteResult.data.isBlocked) {
                                    _state.value.copy(
                                        message = "success",
                                        idUser = remoteResult.data.id,
                                        role = remoteResult.data.role
                                    )
                                        .updateStateUI()
                                } else {
                                    Log.d("TAG check login", "user ${remoteResult.data.name}")
                                    _state.value.copy(
                                        message = "user ${remoteResult.data.name} is blocked"
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