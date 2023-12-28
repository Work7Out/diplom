package diplom.gorinych.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import diplom.gorinych.ui.presentation.admin.addons.AddonsScreen
import diplom.gorinych.ui.presentation.admin.history_screen.HistoryAdminScreen
import diplom.gorinych.ui.presentation.admin.news.NewsScreen
import diplom.gorinych.ui.presentation.admin.statistics.StatisticsAdminScreen
import diplom.gorinych.ui.presentation.admin.users.UsersScreen
import diplom.gorinych.ui.presentation.login_screen.LoginScreen
import diplom.gorinych.ui.presentation.registration_screen.RegistrationScreen
import diplom.gorinych.ui.presentation.splash.SplashScreen
import diplom.gorinych.ui.presentation.user.history_user.HistoryUserScreen
import diplom.gorinych.ui.presentation.user.house_detail.HouseDetailScreen
import diplom.gorinych.ui.presentation.user.list_houses_screen.ListHousesUserScreen
import diplom.gorinych.ui.presentation.user.news.NewsUserScreen
import diplom.gorinych.ui.presentation.user.settings.SettingsScreen

@Composable
fun NavController(
    startDestination: String = "splashScreen",
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(NavigationDestination.SplashScreenDestination.destination) {
            SplashScreen(
                navController
            )
        }
        composable(NavigationDestination.LoginScreenDestination.destination) {
            LoginScreen(
                navController
            )
        }
        composable(NavigationDestination.RegistrationScreenDestination.destination) {
            RegistrationScreen(
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
            NavigationDestination.HistoryUserDestination.destination,
            arguments = listOf(navArgument("idUser") {
                type = NavType.IntType
            })
        ) { HistoryUserScreen(navController) }
        composable(
            NavigationDestination.ListUsersDestination.destination,
            arguments = listOf(navArgument("idUser") {
                type = NavType.IntType
            })
        ) { UsersScreen(navController) }
        composable(
            NavigationDestination.ListHistoryAdminDestination.destination,
            arguments = listOf(navArgument("idUser") {
                type = NavType.IntType
            })
        ) { HistoryAdminScreen(navController) }
        composable(
            NavigationDestination.StatisticsAdminDestination.destination,
            arguments = listOf(navArgument("idUser") {
                type = NavType.IntType
            })
        ) { StatisticsAdminScreen(navController) }
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
        composable(
            NavigationDestination.AddonScreenDestination.destination,
            arguments = listOf(navArgument("idUser") {
                type = NavType.IntType
            })
        ) {
            AddonsScreen(
                navController
            )
        }
        composable(
            NavigationDestination.NewsScreenDestination.destination,
            arguments = listOf(navArgument("idUser") {
                type = NavType.IntType
            })
        ) {
            NewsScreen(
                navController
            )
        }
        composable(
            NavigationDestination.NewsUserScreenDestination.destination,
            arguments = listOf(navArgument("idUser") {
                type = NavType.IntType
            })
        ) {
            NewsUserScreen(
                navController
            )
        }
        composable(
            NavigationDestination.SettingsScreenDestination.destination,
            arguments = listOf(navArgument("idUser") {
                type = NavType.IntType
            })
        ) {
            SettingsScreen(
                navController
            )
        }
    }
}