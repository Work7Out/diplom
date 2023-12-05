package diplom.gorinych.ui.presentation.admin.news


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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import diplom.gorinych.R
import diplom.gorinych.ui.theme.blue
import diplom.gorinych.ui.theme.lightGrey
import diplom.gorinych.ui.theme.secondText
import diplom.gorinych.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogUpdateNote(
    modifier: Modifier = Modifier,
    title:String,
    content: String,
    id: Int,
    isShowDialog: MutableState<Boolean>,
    onEvent: (NewsScreenEvent) -> Unit
) {
    val valueTitle = remember { mutableStateOf(title) }
    val valueContent = remember { mutableStateOf(content) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = lightGrey)
            .padding(10.dp)
    ) {
        TextField(
            modifier = modifier
                .fillMaxWidth(),
            value = valueTitle.value,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.enter_title_news),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.gilroy)),
                        fontWeight = FontWeight(500),
                        color = secondText,
                    )
                )
            },
            onValueChange = {
                valueTitle.value = it
            }
        )
        Spacer(modifier = modifier.height(10.dp))
        TextField(
            modifier = modifier
                .fillMaxWidth(),
            value = valueContent.value,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.enter_content_news),
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.gilroy)),
                        fontWeight = FontWeight(500),
                        color = secondText,
                    )
                )
            },
            onValueChange = { text ->
                valueContent.value = text
            },
        )
        Spacer(modifier = modifier.height(26.dp))
        Button(
            modifier = modifier
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally),
            onClick = {
                onEvent(
                    NewsScreenEvent.UpdateNote(
                        id = id,
                        title = valueTitle.value,
                        content = valueContent.value
                    )
                )
                isShowDialog.value = false
            },
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = blue
            ),
            enabled = valueTitle.value.isNotEmpty()
                    && valueContent.value.isNotEmpty()
        ) {
            Text(
                text = stringResource(id = R.string.update_note),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy)),
                    fontWeight = FontWeight(700),
                    color = white,
                )
            )
        }
    }
}