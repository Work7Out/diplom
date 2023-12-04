package diplom.gorinych.ui.presentation.admin.history_screen

import diplom.gorinych.domain.model.Reserve

data class HistoryScreenState(
    val reserves: List<Reserve> = emptyList(),
    val message:String? = null,
    val idUser: Int = -1,
    val countNewReserves: Int = 0
)
