package diplom.gorinych.ui.presentation.admin.users

import diplom.gorinych.domain.model.User

sealed class UsersScreenEvent {
    class OnChangeRoleUser(val role: String, val user: User) : UsersScreenEvent()
    class OnChangeStatusBlock(val user: User): UsersScreenEvent()
}
