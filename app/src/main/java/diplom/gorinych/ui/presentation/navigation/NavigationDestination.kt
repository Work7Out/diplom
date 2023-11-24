package diplom.gorinych.ui.presentation.navigation

sealed class NavigationDestination (val destination: String){
    object LoginScreenDestination:NavigationDestination("loginScreen")
    object ListHouseUserDestination:NavigationDestination("listHousesUserScreen/{idUser}")
    object HouseDetailDestination:NavigationDestination("houseDetailScreen/{idUser}/{idHouse}")
}
