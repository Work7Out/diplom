package diplom.gorinych.ui.presentation.admin.users

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.repository.HouseRepository
import diplom.gorinych.domain.repository.MailRepository
import diplom.gorinych.domain.utils.BLOCKED
import diplom.gorinych.domain.utils.Resource
import diplom.gorinych.domain.utils.USER
import diplom.gorinych.domain.utils.USER_BLOCKED
import diplom.gorinych.ui.presentation.admin.users.UsersScreenEvent.OnChangeRoleUser
import diplom.gorinych.ui.presentation.admin.users.UsersScreenEvent.OnChangeStatusBlock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

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
            async { loadData() }.onAwait
            async { loadNewReserves() }.onAwait
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
                }
            }

            is OnChangeStatusBlock -> {
                viewModelScope.launch {
                    repository.updateUser(
                        usersScreenEvent.user.copy(
                            isBlocked = !usersScreenEvent.user.isBlocked
                        )
                    )
                }
                viewModelScope.launch(Dispatchers.IO) {
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
        val resultUser = repository.getAllUsers()
        resultUser.collect {
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


    private fun UsersScreenState.updateStateUI() {
        _state.update {
            this
        }
    }
}