package diplom.gorinych.domain.model

interface RoleState {
    object Idle:RoleState
    object AdminRole:RoleState
    object UserRole:RoleState
}