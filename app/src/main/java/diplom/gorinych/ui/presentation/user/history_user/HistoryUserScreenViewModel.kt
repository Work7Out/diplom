package diplom.gorinych.ui.presentation.user.history_user


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.repository.HouseRepository
import diplom.gorinych.domain.utils.Resource.Error
import diplom.gorinych.domain.utils.Resource.Success
import diplom.gorinych.ui.presentation.user.history_user.HistoryUserEvent.OnDeleteReserve
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HistoryUserScreenViewModel @Inject constructor(
    private val repository: HouseRepository,
    private val savedStateHandle: SavedStateHandle
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
                    repository.deleteReserve(historyUserEvent.reserve)
                    getHistory(_state.value.idUser)
                }
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
            getHistory(_state.value.idUser)
        }
    }

    private suspend fun getHistory(id: Int) {
        when (val result = repository.getReserveByUser(id)) {
            is Error -> {
                _state.value.copy(
                    message = result.message
                )
                    .updateStateUI()
            }

            is Success -> {
                _state.value.copy(
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