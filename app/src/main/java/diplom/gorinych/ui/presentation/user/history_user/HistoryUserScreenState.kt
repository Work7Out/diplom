package diplom.gorinych.ui.presentation.user.history_user

import diplom.gorinych.domain.model.Reserve

data class HistoryUserScreenState (
    val message:String? = null,
    val idUser: Int = -1,
    val reserves: List<Reserve> = emptyList()
)