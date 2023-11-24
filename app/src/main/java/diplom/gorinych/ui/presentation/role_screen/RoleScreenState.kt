package diplom.gorinych.ui.presentation.role_screen

import diplom.gorinych.domain.model.RoleState

data class RoleScreenState(
    val roleState: RoleState = RoleState.Idle,
    val message:String? = null,
)
