package diplom.gorinych.ui.presentation.admin.news

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import diplom.gorinych.R
import diplom.gorinych.domain.model.Note
import diplom.gorinych.ui.theme.baseText
import diplom.gorinych.ui.theme.blue
import diplom.gorinych.ui.theme.secondText
import diplom.gorinych.ui.theme.thirdText
import diplom.gorinych.ui.theme.white

@Composable
fun ItemNote(
    modifier: Modifier = Modifier,
    note: Note,
    onEvent: (NewsScreenEvent) -> Unit
) {
    val isShowDialog = remember { mutableStateOf(false) }

    if (isShowDialog.value) {
        Dialog(onDismissRequest = { isShowDialog.value = false }) {
            DialogUpdateNote(
                title = note.title,
                content = note.content,
                id = note.id,
                isShowDialog = isShowDialog,
                onEvent = onEvent
            )
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .clickable {
                isShowDialog.value = true
            }
            .background(color = white)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = modifier.weight(4f)
        ) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = note.title,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy)),
                    fontWeight = FontWeight(600),
                    color = baseText
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = modifier.height(5.dp))
            Text(
                text = note.content,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy)),
                    fontWeight = FontWeight(600),
                    color = secondText
                )
            )
            Spacer(modifier = modifier.height(5.dp))
            Text(
                modifier = modifier.fillMaxWidth(),
                text = note.dateCreate,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy)),
                    fontWeight = FontWeight(600),
                    color = thirdText
                ),
                textAlign = TextAlign.End
            )
        }
        Spacer(modifier = modifier.width(15.dp))
        Button(
            modifier = modifier.weight(1f),
            onClick = {
                onEvent(NewsScreenEvent.DeleteNote(note))
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = blue
            )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.baseline_close_24
                ), contentDescription = "",
                tint = white
            )
        }
    }
}