package diplom.gorinych.ui.presentation.admin.history_screen

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
import diplom.gorinych.domain.model.Feedback
import diplom.gorinych.domain.utils.BLOCKED
import diplom.gorinych.domain.utils.UNBLOCKED
import diplom.gorinych.ui.theme.baseText
import diplom.gorinych.ui.theme.blue
import diplom.gorinych.ui.theme.white

@Composable
fun ItemFeedbackScreen(
    modifier: Modifier = Modifier,
    feedback: Feedback,
    onEvent: (HistoryScreenEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = white)
            .padding(10.dp)
    ) {
        Text(
            text = "${stringResource(id = R.string.user_login)} ${feedback.name}",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = baseText,
            )
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = "${stringResource(id = R.string.house_boat)} ${feedback.idHouse}",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = baseText,
            )
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = feedback.content,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = baseText,
            )
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = if (feedback.isBlocked) stringResource(id = R.string.blocked) else stringResource(
                id = R.string.not_blocked
            ),
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = baseText,
            )
        )
        Spacer(modifier = modifier.height(5.dp))
        Text(
            text = "${stringResource(id = R.string.rang)} ${feedback.rang}",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.gilroy)),
                fontWeight = FontWeight(600),
                color = baseText,
            )
        )
        Spacer(modifier = modifier.height(10.dp))
        Button(
            modifier = modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = blue
            ),
            onClick = {
                onEvent(
                    HistoryScreenEvent.OnChangeStatusBlockFeedback(
                        feedback = feedback,
                        message = if (feedback.isBlocked) UNBLOCKED else BLOCKED
                    )
                )
            }) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = if (feedback.isBlocked) stringResource(id = R.string.make_unblocked) else stringResource(
                    id = R.string.make_blocked
                ),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy)),
                    fontWeight = FontWeight(600),
                    color = white,
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}