package diplom.gorinych.ui.presentation.admin.users

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.repository.MailRepository
import diplom.gorinych.domain.repository.RemoteRepository
import diplom.gorinych.domain.utils.BLOCKED
import diplom.gorinych.domain.utils.EMAIL_LOGIN
import diplom.gorinych.domain.utils.EMAIL_PASSWORD
import diplom.gorinych.domain.utils.Resource
import diplom.gorinych.domain.utils.UNBLOCKED
import diplom.gorinych.domain.utils.USER
import diplom.gorinych.domain.utils.USER_BLOCKED
import diplom.gorinych.domain.utils.USER_UNBLOCKED
import diplom.gorinych.domain.utils.WAITING_CONFIRM
import diplom.gorinych.ui.presentation.admin.users.UsersScreenEvent.OnChangeRoleUser
import diplom.gorinych.ui.presentation.admin.users.UsersScreenEvent.OnChangeStatusBlock
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val mailRepository: MailRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(UsersScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val userId = savedStateHandle.get<Int>("idUser") ?: return@launch
            _state.value.copy(
                idUser = userId
            )
                .updateStateUI()
            async { loadData() }.onAwait
            async { loadNewReserves() }.onAwait
        }
    }

    fun onEvent(usersScreenEvent: UsersScreenEvent) {
        when (usersScreenEvent) {
            is OnChangeRoleUser -> {
                viewModelScope.launch {
                    _state.value.copy(
                        isLoading = false,
                    )
                        .updateStateUI()
                    remoteRepository.updateUser(
                        usersScreenEvent.user.copy(
                            role = usersScreenEvent.role
                        )
                    )
                    loadData()
                }
            }

            is OnChangeStatusBlock -> {
                val newStatus = !usersScreenEvent.user.isBlocked
                viewModelScope.launch {
                    _state.value.copy(
                        isLoading = false,
                    )
                        .updateStateUI()
                    remoteRepository.updateUser(
                        usersScreenEvent.user.copy(
                            isBlocked = newStatus
                        )
                    )
                    loadData()
                }
                viewModelScope.launch(Dispatchers.IO) {
                    mailRepository.sendEmail(
                        login = EMAIL_LOGIN,
                        password = EMAIL_PASSWORD,
                        email = usersScreenEvent.user.email,
                        theme = if (newStatus) USER_BLOCKED else USER_UNBLOCKED,
                        content = if (newStatus) "$USER ${usersScreenEvent.user.name} $BLOCKED" else "$USER ${usersScreenEvent.user.name} $UNBLOCKED"
                    )
                }
            }
        }
    }

    private suspend fun loadNewReserves() {
        when (val result = remoteRepository.getHistoryByStatus(status = WAITING_CONFIRM)) {
            is Resource.Error -> {
                _state.value.copy(
                    message = result.message
                )
                    .updateStateUI()
            }

            is Resource.Success -> {
                _state.value.copy(
                    countNewReserves = result.data?.size ?: 0
                )
                    .updateStateUI()
            }
        }

    }

    private suspend fun loadData() {
        when (val resultUser = remoteRepository.getAllUsers()) {
            is Resource.Error -> {
                _state.value.copy(
                    isLoading = false,
                    message = resultUser.message
                )
                    .updateStateUI()
            }

            is Resource.Success -> {
                _state.value.copy(
                    isLoading = false,
                    message = null,
                    users = resultUser.data ?: emptyList()
                )
                    .updateStateUI()
            }
        }
    }


    private fun UsersScreenState.updateStateUI() {
        _state.update {
            this
        }
    }
}