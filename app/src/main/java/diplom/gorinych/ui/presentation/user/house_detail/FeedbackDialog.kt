package diplom.gorinych.ui.presentation.user.house_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import diplom.gorinych.ui.presentation.base.FeedbackStars

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackDialog(
    modifier: Modifier = Modifier,
) {
    val content = remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        FeedbackStars()
        Spacer(modifier = modifier.height(5.dp))
        TextField(
            modifier = modifier
                .fillMaxWidth(),
            value = content.value,
            onValueChange = {
                content.value = it
            })
    }
}