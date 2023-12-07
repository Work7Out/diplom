package diplom.gorinych.ui.presentation.user.house_detail

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import diplom.gorinych.R
import diplom.gorinych.ui.presentation.base.FeedbackStars
import diplom.gorinych.ui.presentation.base.calculateCheckedStar
import diplom.gorinych.ui.theme.Pink80
import diplom.gorinych.ui.theme.blue
import diplom.gorinych.ui.theme.lightGrey
import diplom.gorinych.ui.theme.secondText
import diplom.gorinych.ui.theme.white

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
            .background(color = lightGrey)
            .padding(10.dp)
    ) {
        FeedbackStars(
            starsSwitcher = starsSwitcher
        )
        Spacer(modifier = modifier.height(5.dp))
        TextField(
            modifier = modifier
                .fillMaxWidth(),
            value = content.value,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.enter_feedback),
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.gilroy)),
                        fontWeight = FontWeight(500),
                        color = secondText,
                    )
                )
            },
            onValueChange = {
                content.value = it
            })
        Spacer(modifier = modifier.height(5.dp))
        Button(
            modifier = modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = blue
            ),
            onClick = {
                onEvent(HouseDetailEvent.AddFeedback(
                    content = content.value,
                    rang = starsSwitcher.value.count { it }
                ))
                isShowDialog.value = false
            }) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = stringResource(id = R.string.do_feedback),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy)),
                    fontWeight = FontWeight(700),
                    color = white,
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}