package diplom.gorinych.ui.presentation.navigation

sealed class NavigationDestination (val destination: String){
    object LoginScreenDestination:NavigationDestination("loginScreen")
    object RoleDestination:NavigationDestination("roleScreen/{idUser}")
}
