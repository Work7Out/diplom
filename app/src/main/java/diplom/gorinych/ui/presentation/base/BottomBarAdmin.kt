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
import diplom.gorinych.R.drawable

@Composable
fun BottomBarAdmin(
    navController: NavController,
    modifier: Modifier = Modifier,
    idUser:Int
) {
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
                contentDescription = ""
            )
        }
        IconButton(onClick = {
            navController.navigate("feedbacksScreen/$idUser")
        }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = drawable.baseline_feedback_24),
                contentDescription = ""
            )
        }
        IconButton(onClick = {
            //navController.navigate("historyUserScreen/${state.value.idUser}")
        }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = drawable.baseline_history_24),
                contentDescription = ""
            )
        }
        IconButton(onClick = {
            //navController.navigate("listHousesUserScreen/${state.value.idUser}")
        }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = drawable.baseline_analytics_24),
                contentDescription = ""
            )
        }
    }
}