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
import diplom.gorinych.ui.theme.baseText

@Composable
fun AppBarUser(
    navController: NavController,
    modifier: Modifier = Modifier,
    onSendCall: () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = onSendCall) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_add_call_24),
                contentDescription = "",
                tint = baseText
            )
        }
        IconButton(onClick = {
            navController.navigate("loginScreen") {
                this.popUpTo("loginScreen") {
                    inclusive = true
                }
            }
            onClick.invoke()
        }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_logout_24),
                contentDescription = "",
                tint = baseText
            )
        }
    }
}