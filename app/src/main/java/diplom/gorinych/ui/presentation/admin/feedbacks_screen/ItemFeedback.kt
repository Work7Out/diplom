package diplom.gorinych.ui.presentation.admin.feedbacks_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import diplom.gorinych.domain.model.Feedback
import diplom.gorinych.ui.theme.PurpleGrey80

@Composable
fun ItemFeedbackScreen(
    modifier: Modifier = Modifier,
    feedback: Feedback,
    idUser: Int,
    onEvent: (FeedbackScreenEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = PurpleGrey80)
            .padding(5.dp)
    ) {
        Text(text = "пользователь - ${feedback.idUser}")
        Spacer(modifier = modifier.height(5.dp))
        Text(text = "квартира - ${feedback.idHouse}")
        Spacer(modifier = modifier.height(5.dp))
        Text(text = feedback.content)
        Spacer(modifier = modifier.height(5.dp))
        Text(text = if (feedback.isBlocked) "Заблокирован" else "Не заблокирован")
        Spacer(modifier = modifier.height(5.dp))
        Text(text = "оценка - ${feedback.rang}")
        Spacer(modifier = modifier.height(5.dp))
        Button(
            modifier = modifier
                .fillMaxWidth(),
            onClick = {
                onEvent(FeedbackScreenEvent.OnChangeStatusBlockFeedback(feedback))
            }) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = if (feedback.isBlocked) "Разблокировать" else "Заблокировать",
            )
        }
    }
}