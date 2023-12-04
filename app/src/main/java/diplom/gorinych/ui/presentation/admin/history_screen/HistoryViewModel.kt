package diplom.gorinych.ui.presentation.admin.history_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.repository.HouseRepository
import diplom.gorinych.domain.utils.Resource
import diplom.gorinych.ui.presentation.admin.history_screen.HistoryScreenEvent.OnConfirmReserve
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: HouseRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(HistoryScreenState())
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

    fun onEvent(historyScreenEvent: HistoryScreenEvent) {
        when (historyScreenEvent) {
            is OnConfirmReserve -> {
                viewModelScope.launch {
                    repository.updateHistory(
                        historyScreenEvent.reserve.copy(
                            confirmReservation = historyScreenEvent.status
                        )
                    )
                    loadData()
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
        when (val result = repository.getAllHistory()) {
            is Resource.Error -> {
                _state.value.copy(
                    message = result.message
                )
                    .updateStateUI()
            }

            is Resource.Success -> {
                _state.value.copy(
                    reserves = result.data ?: emptyList()
                )
                    .updateStateUI()
            }
        }
    }

    private fun HistoryScreenState.updateStateUI() {
        _state.update {
            this
        }
    }
}