package diplom.gorinych.ui.presentation.admin.addons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import diplom.gorinych.R
import diplom.gorinych.domain.model.Addon
import diplom.gorinych.ui.theme.baseText
import diplom.gorinych.ui.theme.blue
import diplom.gorinych.ui.theme.white

@Composable
fun ItemAddon(
    modifier: Modifier = Modifier,
    addon: Addon,
    onEvent: (AddonScreenEvent) -> Unit
) {
    val isShowDialog = remember { mutableStateOf(false) }

    if (isShowDialog.value) {
        Dialog(onDismissRequest = { isShowDialog.value = false }) {
            DialogUpdateAddon(
                title = addon.title,
                price = addon.price,
                id = addon.id,
                isShowDialog = isShowDialog,
                onEvent = onEvent
            )
        }
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .clickable {
                isShowDialog.value = true
            }
            .background(color = white)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = modifier.weight(4f)
        ) {
            Text(
                text = addon.title,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy)),
                    fontWeight = FontWeight(600),
                    color = baseText
                )
            )
            Spacer(modifier = modifier.height(5.dp))
            Text(
                text = "${stringResource(id = R.string.price)} ${addon.price}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy)),
                    fontWeight = FontWeight(600),
                    color = blue
                )
            )
        }
        Button(
            modifier = modifier.weight(1f),
            onClick = {
                onEvent(AddonScreenEvent.DeleteAddon(addon))
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = blue
            )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.baseline_close_24
                ), contentDescription = "",
                tint = white
            )
        }
    }
}