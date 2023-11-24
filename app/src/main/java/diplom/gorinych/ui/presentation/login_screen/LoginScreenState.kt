package diplom.gorinych.ui.presentation.login_screen

data class LoginScreenState(
    val message:String? = null,
    val login: String = "",
    val password: String = "",
    val idUser: Int = -1
)