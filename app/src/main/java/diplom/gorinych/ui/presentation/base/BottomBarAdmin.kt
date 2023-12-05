package diplom.gorinych.ui.presentation.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import diplom.gorinych.R
import diplom.gorinych.R.drawable
import diplom.gorinych.ui.presentation.navigation.NavigationDestination
import diplom.gorinych.ui.theme.baseText
import diplom.gorinych.ui.theme.blue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarAdmin(
    navController: NavController,
    modifier: Modifier = Modifier,
    idUser: Int,
    count: Int,
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
            navController.navigate("usersScreen/$idUser")
        }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = drawable.baseline_people_alt_24),
                contentDescription = "",
                tint = if (currentRoute == NavigationDestination.ListUsersDestination.destination) blue else baseText
            )
        }
        IconButton(onClick = {
            navController.navigate("historyAdminScreen/$idUser")
        }) {
            BadgedBox(badge = {
                if (count > 0) {
                    Badge {
                        Text(text = count.toString())
                    }
                }
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = drawable.baseline_history_24),
                    contentDescription = "",
                    tint = if (currentRoute == NavigationDestination.ListHistoryAdminDestination.destination) blue else baseText
                )
            }
        }
        IconButton(onClick = {
            navController.navigate("addonScreen")
        }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = drawable.baseline_add_home_work_24),
                contentDescription = "",
                tint = if (currentRoute == NavigationDestination.AddonScreenDestination.destination) blue else baseText
            )
        }
        IconButton(onClick = {
            navController.navigate("newsScreen")
        }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = drawable.baseline_article_24),
                contentDescription = "",
                tint = if (currentRoute == NavigationDestination.NewsScreenDestination.destination) blue else baseText
            )
        }
        IconButton(onClick = {
            navController.navigate("statisticsAdminScreen/$idUser")
        }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = drawable.baseline_analytics_24),
                contentDescription = "",
                tint = if (currentRoute == NavigationDestination.StatisticsAdminDestination.destination) blue else baseText
            )
        }
    }
}