package diplom.gorinych.ui.presentation.admin.users

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.repository.HouseRepository
import diplom.gorinych.domain.repository.MailRepository
import diplom.gorinych.domain.utils.BLOCKED
import diplom.gorinych.domain.utils.Resource
import diplom.gorinych.domain.utils.SUCCESS_REGISTRATION
import diplom.gorinych.domain.utils.USER
import diplom.gorinych.domain.utils.USER_BLOCKED
import diplom.gorinych.ui.presentation.admin.users.UsersScreenEvent.OnChangeRoleUser
import diplom.gorinych.ui.presentation.admin.users.UsersScreenEvent.OnChangeStatusBlock
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val repository: HouseRepository,
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
            loadData()
            loadNewReserves()
        }
    }

    fun onEvent(usersScreenEvent: UsersScreenEvent) {
        when (usersScreenEvent) {
            is OnChangeRoleUser -> {
                viewModelScope.launch {
                    repository.updateUser(
                        usersScreenEvent.user.copy(
                            role = usersScreenEvent.role
                        )
                    )
                    loadData()
                }
            }

            is OnChangeStatusBlock -> {
                viewModelScope.launch {
                    repository.updateUser(
                        usersScreenEvent.user.copy(
                            isBlocked = !usersScreenEvent.user.isBlocked
                        )
                    )
                    loadData()
                }
                viewModelScope.launch (Dispatchers.IO) {
                    mailRepository.sendEmail(
                        login = "edurda77@gmail.com",
                        password = "Khayarov1977!",
                        email = usersScreenEvent.user.email,
                        theme = USER_BLOCKED,
                        content = "$USER ${usersScreenEvent.user.name} $BLOCKED"
                    )
                }
            }
        }
    }

    private suspend fun loadNewReserves() {
        val result = repository.getHistoryNoConfirmStatus()
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
                        countNewReserves = it.data?.size ?: 0
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private suspend fun loadData() {
        when (val resultUser = repository.getAllUsers()) {
            is Resource.Error -> {
                _state.value.copy(
                    message = resultUser.message
                )
                    .updateStateUI()
            }

            is Resource.Success -> {
                _state.value.copy(
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