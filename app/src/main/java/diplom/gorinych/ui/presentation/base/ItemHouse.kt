package diplom.gorinych.ui.presentation.base

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import diplom.gorinych.R
import diplom.gorinych.domain.model.House
import diplom.gorinych.domain.utils.BASE_URL_IMAGE
import diplom.gorinych.ui.theme.baseText
import diplom.gorinych.ui.theme.secondText
import diplom.gorinych.ui.theme.white

@Composable
fun ItemHouse(
    modifier: Modifier = Modifier,
    house: House,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = white)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = modifier.size(100.dp),
            model = BASE_URL_IMAGE +house.image,
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = modifier.width(10.dp))
        Column(
            modifier = modifier.weight(1f)
        ) {
            Text(
                text = house.name,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy)),
                    fontWeight = FontWeight(600),
                    color = baseText
                )
            )
            Spacer(modifier = modifier.height(10.dp))
            Text(
                text = "${stringResource(id = R.string.price)} ${house.price} ${stringResource(id = R.string.byn)}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy)),
                    fontWeight = FontWeight(600),
                    color = secondText
                )
            )
        }
    }
}
