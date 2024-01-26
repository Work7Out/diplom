package diplom.gorinych.ui.presentation.admin.statistics

import diplom.gorinych.domain.model.Call
import diplom.gorinych.domain.model.Reserve

data class StatisticsScreenState(
    val reserves: List<Reserve> = emptyList(),
    val message:String? = null,
    val idUser: Int = -1,
    val countOrders: Int = 0,
    val countConfirmOrders: Int = 0,
    val amountAll: Double = 0.0,
    val amountLastMonth: Double = 0.0,
    val amountLastSeason: Double = 0.0,
    val countNewReserves: Int = 0,
    val isLoading: Boolean = true,
    val calls: List<Call> = emptyList(),
)