package diplom.gorinych.ui.presentation.user.history_user

import diplom.gorinych.domain.model.Reserve
import diplom.gorinych.domain.model.User

data class HistoryUserScreenState (
    val message:String? = null,
    val idUser: Int = -1,
    val user: User? =null,
    val reserves: List<Reserve> = emptyList()
)