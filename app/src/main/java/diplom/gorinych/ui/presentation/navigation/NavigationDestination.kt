package diplom.gorinych.ui.presentation.navigation

sealed class NavigationDestination (val destination: String){
    object SplashScreenDestination:NavigationDestination("splashScreen")
    object LoginScreenDestination:NavigationDestination("loginScreen")
    object RegistrationScreenDestination:NavigationDestination("registrationScreen")
    object ListHouseUserDestination:NavigationDestination("listHousesUserScreen/{idUser}")
    object HistoryUserDestination:NavigationDestination("historyUserScreen/{idUser}")
    object ListUsersDestination:NavigationDestination("usersScreen/{idUser}")
    object ListHistoryAdminDestination:NavigationDestination("historyAdminScreen/{idUser}")
    object StatisticsAdminDestination:NavigationDestination("statisticsAdminScreen/{idUser}")
    object HouseDetailDestination:NavigationDestination("houseDetailScreen/{idUser}/{idHouse}")
    object AddonScreenDestination:NavigationDestination("addonScreen/{idUser}")
    object NewsScreenDestination:NavigationDestination("newsScreen/{idUser}")
    object SettingsScreenDestination:NavigationDestination("settingsScreen/{idUser}")
    object NewsUserScreenDestination:NavigationDestination("newsUserScreen/{idUser}")
}
