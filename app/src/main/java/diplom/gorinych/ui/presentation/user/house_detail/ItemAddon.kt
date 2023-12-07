package diplom.gorinych.ui.presentation.user.house_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import diplom.gorinych.R
import diplom.gorinych.domain.model.Addon
import diplom.gorinych.ui.theme.baseText
import diplom.gorinych.ui.theme.blue
import diplom.gorinych.ui.theme.grey
import diplom.gorinych.ui.theme.white

@Composable
fun ItemAddon(
    modifier: Modifier = Modifier,
    addon: Addon,
    isSelected: Boolean,
    onEvent: (HouseDetailEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onEvent(HouseDetailEvent.AddAddon(addon))
            }
            .border(width = 1.dp, color = blue, shape = RoundedCornerShape(5.dp))
            .clip(shape = RoundedCornerShape(5.dp))
            .background(color = if (isSelected) blue else grey)
            .padding(5.dp)
    )
    {
        Text(
            modifier = modifier
                .fillMaxWidth(),
            text = addon.title,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = if (isSelected) white else baseText
            )
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(
            modifier = modifier
                .fillMaxWidth(),
            text = "${stringResource(id = R.string.price)} ${addon.price} ${
                stringResource(
                    id = R.string.byn
                )
            }",
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = if (isSelected) white else baseText
            ),
            textAlign = TextAlign.End
        )
    }
}