package diplom.gorinych.ui.presentation.user.house_detail

import diplom.gorinych.domain.model.User

sealed class UsersScreenEvent {
    class OnChangeRoleUser(val role: String, val user: User) : UsersScreenEvent()
    class OnChangeStatusBlock(val user: User):UsersScreenEvent()
}
