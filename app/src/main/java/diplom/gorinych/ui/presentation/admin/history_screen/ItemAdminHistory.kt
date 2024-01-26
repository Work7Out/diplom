package diplom.gorinych.ui.presentation.admin.history_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import diplom.gorinych.R
import diplom.gorinych.domain.model.Reserve
import diplom.gorinych.ui.theme.baseText
import diplom.gorinych.ui.theme.blue
import diplom.gorinych.ui.theme.white

@Composable
fun ItemAdminHistory(
    modifier: Modifier = Modifier,
    reserve: Reserve,
    onEvent: (HistoryScreenEvent) -> Unit,
    statuses: List<String>
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = white)
            .padding(10.dp)
    ) {
        Text(
            text = "${stringResource(id = R.string.user_login)} ${reserve.idUser}",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = baseText,
            )
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = "${stringResource(id = R.string.house_boat)} ${reserve.idHouse}",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = baseText,

                )
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = "${stringResource(id = R.string.begin)} ${reserve.dateBegin}",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = baseText,

                )
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = "${stringResource(id = R.string.end)} ${reserve.dateEnd}",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = baseText,

                )
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = "${stringResource(id = R.string.reserve_amount)} ${reserve.amount}",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = baseText,

                )
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = "${stringResource(id = R.string.state_reserve)} ${reserve.confirmReservation}",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = baseText,

                )
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = "${stringResource(id = R.string.additional)} ${reserve.additions}",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = baseText,

                )
        )
        Spacer(modifier = modifier.height(5.dp))
        statuses.forEach { status ->
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                RadioButton(
                    selected = (status == reserve.confirmReservation),
                    colors = RadioButtonDefaults.colors(
                        selectedColor = blue,
                        unselectedColor = blue
                    ),
                    onClick = {
                        onEvent(
                            HistoryScreenEvent.OnConfirmReserve(
                                reserve = reserve,
                                status = status
                            )
                        )
                    }
                )
                Text(
                    text = status,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.gilroy)),
                        fontWeight = FontWeight(600),
                        color = baseText
                    )
                )
            }
        }
    }
}