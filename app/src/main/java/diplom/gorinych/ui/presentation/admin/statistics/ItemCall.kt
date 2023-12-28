package diplom.gorinych.ui.presentation.admin.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import diplom.gorinych.domain.model.Call
import diplom.gorinych.ui.presentation.admin.users.UsersScreenEvent
import diplom.gorinych.ui.theme.baseText
import diplom.gorinych.ui.theme.blue
import diplom.gorinych.ui.theme.secondText
import diplom.gorinych.ui.theme.white

@Composable
fun ItemCall(
    modifier: Modifier = Modifier,
    call: Call,
    onEvent: (StatisticScreenEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = white)
            .padding(10.dp),
    ) {
        Text(
            text = "${stringResource(id = R.string.name)} ${call.name}",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = baseText
            ),
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = "${stringResource(id = R.string.phone)} ${call.phone}",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = secondText
            )
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = if (call.isResponse) {
                "${stringResource(id = R.string.status_call)} ${stringResource(id = R.string.recall)}"
            } else {
                "${stringResource(id = R.string.status_call)} ${stringResource(id = R.string.not_recall)}"
            },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = secondText
            )
        )
        Spacer(modifier = modifier.height(5.dp))
        Button(
            modifier = modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = blue
            ),
            onClick = {
                onEvent(
                    StatisticScreenEvent.OnUpdatePhone(
                        id = call.id,
                        name = call.name,
                        phone = call.phone,
                        isResponse = !call.isResponse
                    )
                )
            }) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = if (call.isResponse) stringResource(id = R.string.not_recall) else stringResource(
                    id = R.string.recall
                ),
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