package diplom.gorinych.ui.presentation.login_screen

sealed class LoginEvent{
    class SetLogin(val login: String):LoginEvent()
    class SetPassword(val password: String):LoginEvent()
    object OnLogin:LoginEvent()
}
