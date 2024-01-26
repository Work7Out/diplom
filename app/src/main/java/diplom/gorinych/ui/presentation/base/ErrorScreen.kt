package diplom.gorinych.ui.presentation.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import diplom.gorinych.R.font
import diplom.gorinych.ui.theme.baseText
import diplom.gorinych.ui.theme.grey

@Composable
fun ErrorScreen (
    modifier: Modifier = Modifier,
    error:String
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = grey),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = modifier
                .fillMaxWidth(),
            text = error,
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(font.gilroy)),
                fontWeight = FontWeight(700),
                color = baseText,
            ),
            textAlign = TextAlign.Center
        )
    }
}