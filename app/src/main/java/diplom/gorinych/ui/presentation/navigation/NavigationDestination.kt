package diplom.gorinych.ui.presentation.navigation

sealed class NavigationDestination (val destination: String){
    object LoginScreenDestination:NavigationDestination("loginScreen")
    object ListHouseUserDestination:NavigationDestination("listHousesUserScreen/{idUser}")
    object HistoryUserDestination:NavigationDestination("historyUserScreen/{idUser}")
    object ListUsersDestination:NavigationDestination("usersScreen/{idUser}")
    object ListFeedbacksDestination:NavigationDestination("feedbacksScreen/{idUser}")
    object ListHistoryAdminDestination:NavigationDestination("historyAdminScreen/{idUser}")
    object HouseDetailDestination:NavigationDestination("houseDetailScreen/{idUser}/{idHouse}")
}
