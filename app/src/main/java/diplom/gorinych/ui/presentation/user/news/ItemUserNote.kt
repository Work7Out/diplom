package diplom.gorinych.ui.presentation.user.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import diplom.gorinych.R
import diplom.gorinych.domain.model.Note
import diplom.gorinych.ui.theme.baseText
import diplom.gorinych.ui.theme.secondText
import diplom.gorinych.ui.theme.thirdText
import diplom.gorinych.ui.theme.white

@Composable
fun ItemUserNote(
    modifier: Modifier = Modifier,
    note: Note,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
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
    }
}