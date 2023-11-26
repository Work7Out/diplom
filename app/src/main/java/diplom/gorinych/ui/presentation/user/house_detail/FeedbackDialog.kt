package diplom.gorinych.ui.presentation.user.house_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import diplom.gorinych.ui.presentation.base.FeedbackStars
import diplom.gorinych.ui.presentation.base.calculateCheckedStar
import diplom.gorinych.ui.theme.Pink80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackDialog(
    modifier: Modifier = Modifier,
    onEvent: (HouseDetailEvent) -> Unit,
    isShowDialog: MutableState<Boolean>,
) {
    val content = remember {
        mutableStateOf("")
    }

    val starsSwitcher = remember {
        mutableStateOf(calculateCheckedStar(0))
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Pink80)
            .padding(5.dp)
    ) {
        FeedbackStars(
            starsSwitcher = starsSwitcher
        )
        Spacer(modifier = modifier.height(5.dp))
        TextField(
            modifier = modifier
                .fillMaxWidth(),
            value = content.value,
            onValueChange = {
                content.value = it
            })
        Spacer(modifier = modifier.height(5.dp))
        Button(
            modifier = modifier
                .fillMaxWidth(),
            onClick = {
                onEvent(HouseDetailEvent.AddFeedback(
                    content = content.value,
                    rang = starsSwitcher.value.count { it }
                ))
                isShowDialog.value = false
            }) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = "Оставить отзыв",
            )
        }
    }
}