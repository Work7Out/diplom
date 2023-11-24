package diplom.gorinych.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import diplom.gorinych.ui.presentation.login_screen.LoginScreen
import diplom.gorinych.ui.presentation.user.house_detail.HouseDetailScreen
import diplom.gorinych.ui.presentation.user.list_houses_screen.ListHousesUserScreen

@Composable
fun NavController(
    startDestination: String = "loginScreen",
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(NavigationDestination.LoginScreenDestination.destination) {
            LoginScreen(
                navController
            )
        }
        composable(
            NavigationDestination.ListHouseUserDestination.destination,
            arguments = listOf(navArgument("idUser") {
                type = NavType.IntType
            })
        ) { ListHousesUserScreen(navController) }
        composable(
            NavigationDestination.HouseDetailDestination.destination,
            arguments = listOf(
                navArgument("idUser") {
                    type = NavType.IntType
                },
                navArgument("idHouse") {
                    type = NavType.IntType
                })
        ) { HouseDetailScreen(navController) }
    }
}