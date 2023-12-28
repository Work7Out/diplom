package diplom.gorinych.ui.presentation.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import diplom.gorinych.domain.model.Reserve
import diplom.gorinych.ui.theme.baseText
import diplom.gorinych.ui.theme.blue
import diplom.gorinych.ui.theme.secondText
import diplom.gorinych.ui.theme.white

@Composable
fun ItemHistory(
    modifier: Modifier = Modifier,
    reserve: Reserve,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = white)
            .padding(10.dp)
    ) {
        Text(
            text = "${reserve.dateBegin} - ${reserve.dateEnd}",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = baseText
            )
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = "${stringResource(id = R.string.amount_reserve)} ${reserve.amount} ${stringResource(id = R.string.byn)} ",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = baseText
            )
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = "${stringResource(id = R.string.status)} ${reserve.confirmReservation}",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = baseText
            )
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = "${stringResource(id = R.string.additional)} ${reserve.additions}",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = secondText,

                )
        )
        Spacer(modifier = modifier.height(10.dp))
        Button(
            modifier = modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = blue
            ),
            onClick = onClick
        ) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = stringResource(id = R.string.delete_reserve),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy)),
                    fontWeight = FontWeight(600),
                    color = white
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}