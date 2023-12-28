package diplom.gorinych.ui.presentation.admin.statistics

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.model.Call
import diplom.gorinych.domain.repository.RemoteRepository
import diplom.gorinych.domain.repository.SharedRepository
import diplom.gorinych.domain.utils.GET_IS_AWAITED
import diplom.gorinych.domain.utils.Resource
import diplom.gorinych.domain.utils.calculateAllSum
import diplom.gorinych.domain.utils.calculateConfirmOrders
import diplom.gorinych.domain.utils.calculateMonthSum
import diplom.gorinych.domain.utils.calculateSeasonSum
import kotlinx.coroutines.async
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val savedStateHandle: SavedStateHandle,
    private val sharedRepository: SharedRepository
) : ViewModel() {
    private val _state = MutableStateFlow(StatisticsScreenState())
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
            async { loadCalls() }.onAwait
        }
    }

    private suspend fun loadData() {
        when (val result = remoteRepository.getAllHistory()) {
            is Resource.Error -> {
                _state.value.copy(
                    isLoading = false,
                    message = result.message
                )
                    .updateStateUI()
            }

            is Resource.Success -> {
                _state.value.copy(
                    isLoading = false,
                    reserves = result.data ?: emptyList(),
                )
                    .updateStateUI()
                _state.value.copy(
                    countOrders = _state.value.reserves.size,
                    countConfirmOrders = calculateConfirmOrders(_state.value.reserves),
                    amountAll = calculateAllSum(_state.value.reserves),
                    amountLastMonth = calculateMonthSum(_state.value.reserves),
                    amountLastSeason = calculateSeasonSum(_state.value.reserves)
                )
                    .updateStateUI()
            }
        }
    }

    private suspend fun loadNewReserves() {
        when (val result = remoteRepository.getHistoryByStatus(status = GET_IS_AWAITED)) {
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

    private suspend fun loadCalls() {
        when (val result = remoteRepository.getAllCalls()) {
            is Resource.Error -> {
                _state.value.copy(
                    isLoading = false,
                    message = result.message
                )
                    .updateStateUI()
            }

            is Resource.Success -> {
                _state.value.copy(
                    isLoading = false,
                    calls = result.data ?: emptyList()
                )
                    .updateStateUI()
            }
        }
    }

    fun onEvent(event: StatisticScreenEvent) {
        when (event) {
            StatisticScreenEvent.Exit -> {
                sharedRepository.setUser(-1)
                sharedRepository.setRole("")
            }

            is StatisticScreenEvent.OnUpdatePhone -> {
                viewModelScope.launch {
                    remoteRepository.updateCall(
                        Call(
                            id = event.id,
                            name = event.name,
                            phone = event.phone,
                            isResponse = event.isResponse
                        )
                    )
                    loadCalls()
                }
            }
        }
    }

    private fun StatisticsScreenState.updateStateUI() {
        _state.update {
            this
        }
    }
}