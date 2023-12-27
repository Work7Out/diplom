package diplom.gorinych.ui.presentation.login_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.repository.RemoteRepository
import diplom.gorinych.domain.utils.BLOCKED
import diplom.gorinych.domain.utils.INCORRECT_LOGIN
import diplom.gorinych.domain.utils.Resource
import diplom.gorinych.domain.utils.SUCCESS
import diplom.gorinych.domain.utils.USER
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
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
                                    message = INCORRECT_LOGIN
                                )
                                    .updateStateUI()
                            } else {
                                if (!remoteResult.data.isBlocked) {
                                    _state.value.copy(
                                        message = SUCCESS,
                                        idUser = remoteResult.data.id,
                                        role = remoteResult.data.role
                                    )
                                        .updateStateUI()
                                } else {
                                    Log.d("TAG check login", "user ${remoteResult.data.name}")
                                    _state.value.copy(
                                        message = "$USER ${remoteResult.data.name} $BLOCKED"
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