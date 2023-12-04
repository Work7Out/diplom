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
import diplom.gorinych.ui.theme.baseText
import diplom.gorinych.ui.theme.blue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarAdmin(
    navController: NavController,
    modifier: Modifier = Modifier,
    count: Int
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = {
        //    navController.navigate("usersScreen/$idUser")
        }) {
            BadgedBox(badge = {
                if (count>0) {
                    Badge {
                        Text(text = count.toString())
                    }
                }
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_campaign_24),
                    contentDescription = "",
                    tint = blue
                )
            }
        }
        IconButton(onClick = {
            navController.navigate("loginScreen") {
                this.popUpTo("loginScreen") {
                    inclusive = true
                }
            }
        }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_logout_24),
                contentDescription = "",
                tint = baseText
            )
        }
    }
}