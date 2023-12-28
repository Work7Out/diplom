package diplom.gorinych.ui.presentation.login_screen

data class LoginScreenState(
    val message:String? = null,
    val login: String = "",
    val password: String = "",
    val idUser: Int = -1,
    val role: String = "",
    val savedUser: SavedUser = SavedUser.NotSaved
)

sealed interface SavedUser{
    object Saved:SavedUser
    object NotSaved:SavedUser
}