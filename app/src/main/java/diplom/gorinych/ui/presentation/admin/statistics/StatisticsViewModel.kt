package diplom.gorinych.ui.presentation.admin.statistics

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.domain.repository.HouseRepository
import diplom.gorinych.domain.utils.Resource
import diplom.gorinych.domain.utils.calculateAllSum
import diplom.gorinych.domain.utils.calculateComfirmOrders
import diplom.gorinych.domain.utils.calculateMonthSum
import diplom.gorinych.domain.utils.calculateSeasonSum
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val repository: HouseRepository,
    private val savedStateHandle: SavedStateHandle
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
            loadData()
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
                    reserves = result.data ?: emptyList(),
                )
                    .updateStateUI()
                _state.value.copy(
                    countOrders = _state.value.reserves.size,
                    countConfirmOrders = calculateComfirmOrders(_state.value.reserves),
                    amountAll = calculateAllSum(_state.value.reserves),
                    amountLastMonth = calculateMonthSum(_state.value.reserves),
                    amountLastSeason = calculateSeasonSum(_state.value.reserves)
                )
                    .updateStateUI()
            }
        }
    }

    private fun StatisticsScreenState.updateStateUI() {
        _state.update {
            this
        }
    }
}