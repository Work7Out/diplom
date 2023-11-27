package diplom.gorinych.ui.presentation.registration_screen

import diplom.gorinych.domain.model.User

data class RegistrationScreenState (
    val users: List<User> = emptyList(),
    val message:String? = null,
)