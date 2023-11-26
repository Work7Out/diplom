package diplom.gorinych.ui.presentation.admin.users

import diplom.gorinych.domain.model.User

data class UsersScreenState(
    val users: List<User> = emptyList(),
    val message:String? = null,
    val idUser: Int = -1,
)
