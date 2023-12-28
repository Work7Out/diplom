package diplom.gorinych.ui.presentation.user.history_user


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.repository.RemoteRepository
import diplom.gorinych.domain.repository.SharedRepository
import diplom.gorinych.domain.utils.Resource.Error
import diplom.gorinych.domain.utils.Resource.Success
import diplom.gorinych.ui.presentation.user.history_user.HistoryUserEvent.OnDeleteReserve
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryUserScreenViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val savedStateHandle: SavedStateHandle,
    private val sharedRepository: SharedRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HistoryUserScreenState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }


    fun onEvent(historyUserEvent: HistoryUserEvent) {
        when (historyUserEvent) {
            is OnDeleteReserve -> {
                viewModelScope.launch {
                    remoteRepository.deleteHistory(historyUserEvent.reserve.id)
                    getHistory()
                }
            }

            HistoryUserEvent.OnSendCall -> {
                viewModelScope.launch {
                    remoteRepository.addNewCall(
                        name = _state.value.user?.name ?: "",
                        phone = _state.value.user?.phone ?: ""
                    )
                }
            }

            HistoryUserEvent.Exit -> {
                sharedRepository.setUser(-1)
                sharedRepository.setRole("")
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            val userId = savedStateHandle.get<Int>("idUser") ?: return@launch
            _state.value.copy(
                idUser = userId,
            )
                .updateStateUI()
            async { getHistory() }.onAwait
            async { loadUserData() }.onAwait
        }
    }

    private suspend fun loadUserData() {
        when (val resultUser = remoteRepository.getUserBiId(_state.value.idUser)) {
            is Error -> {
                _state.value.copy(
                    message = resultUser.message
                )
                    .updateStateUI()
            }

            is Success -> {
                _state.value.copy(
                    user = resultUser.data
                )
                    .updateStateUI()
            }
        }
    }

    private suspend fun getHistory() {
        when (val result = remoteRepository.getHistoryByUser(_state.value.idUser)) {
            is Error -> {
                _state.value.copy(
                    isLoading = false,
                    message = result.message
                )
                    .updateStateUI()
            }

            is Success -> {
                _state.value.copy(
                    isLoading = false,
                    reserves = result.data ?: emptyList()
                )
                    .updateStateUI()
            }
        }
    }

    private fun HistoryUserScreenState.updateStateUI() {
        _state.update {
            this
        }
    }
}