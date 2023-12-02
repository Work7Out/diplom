package diplom.gorinych.ui.presentation.login_screen

sealed interface LoginScreenWindowState {
    object SplashScreen:LoginScreenWindowState
    object BaseScreen:LoginScreenWindowState
}