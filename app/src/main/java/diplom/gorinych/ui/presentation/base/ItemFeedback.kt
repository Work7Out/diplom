package diplom.gorinych.ui.presentation.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import diplom.gorinych.domain.model.Feedback
import diplom.gorinych.ui.theme.PurpleGrey80

@Composable
fun ItemFeedback(
    modifier: Modifier = Modifier,
    feedback: Feedback,
    name: String
) {
    if (!feedback.isBlocked) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(color = PurpleGrey80)
                .padding(5.dp)
        ) {
            FavoriteStar(
                modifier = modifier.align(Alignment.TopEnd),
                rangDepMax = feedback.rang
            )
            Column(
                modifier = modifier
                    .padding(end=20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = modifier
                        .fillMaxWidth(),
                    text = feedback.content
                )
                Spacer(modifier = modifier.height(5.dp))
                Text(
                    modifier = modifier
                        .fillMaxWidth(),
                    text = "$name ${feedback.dateFeedback}",
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Preview
@Composable
private fun SampleItemFeedback() {
    ItemFeedback(
        feedback = Feedback(
            id =1,
            idUser = 1,
            idHouse = 1,
            isBlocked = false,
            content = "ddfdjfdhj fvgffgf",
            rang = 3,
            dateFeedback = "2023-03-31"
        ),
        name = "Petia"
    )
}