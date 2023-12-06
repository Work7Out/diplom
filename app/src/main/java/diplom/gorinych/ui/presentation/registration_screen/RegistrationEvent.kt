package diplom.gorinych.ui.presentation.registration_screen

sealed class RegistrationEvent {
    class OnRegistrationUser(
        val name: String,
        val password: String,
        val phone: String,
        val email: String,
    ) : RegistrationEvent()
}
