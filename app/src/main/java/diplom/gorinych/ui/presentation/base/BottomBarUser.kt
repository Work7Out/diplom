package diplom.gorinych.ui.presentation.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import diplom.gorinych.R
import diplom.gorinych.ui.presentation.navigation.NavigationDestination
import diplom.gorinych.ui.theme.baseText
import diplom.gorinych.ui.theme.blue

@Composable
fun BottomBarUser (
    navController: NavController,
    modifier: Modifier = Modifier,
    idUser: Int,
) {
    val currentRoute = navController.currentDestination?.route
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(onClick = {
            navController.navigate("listHousesUserScreen/$idUser")
        }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_house_24),
                contentDescription = "",
                tint = if (currentRoute == NavigationDestination.ListHouseUserDestination.destination) blue else baseText
            )
        }
        IconButton(onClick = {
            navController.navigate("historyUserScreen/${idUser}")
        }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_history_24),
                contentDescription = "",
                tint = if (currentRoute == NavigationDestination.HistoryUserDestination.destination) blue else baseText
            )
        }
        IconButton(onClick = {
            navController.navigate("settingsScreen/${idUser}")
        }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_construction_24),
                contentDescription = "",
                tint = if (currentRoute == NavigationDestination.SettingsScreenDestination.destination) blue else baseText
            )
        }
        /*IconButton(onClick = {
            navController.navigate("loginScreen") {
                this.popUpTo("loginScreen") {
                    inclusive = true
                }
            }
        }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_logout_24),
                contentDescription = ""
            )
        }*/
    }
}