package diplom.gorinych.ui.presentation.user.list_houses_screen

import diplom.gorinych.domain.model.House
import diplom.gorinych.domain.model.User

data class ListHousesScreenState(
    val message:String? = null,
    val houses: List<House> = emptyList(),
    val user: User? = null
)
