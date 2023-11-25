package diplom.gorinych.ui.presentation.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import diplom.gorinych.R
import diplom.gorinych.ui.theme.white
import diplom.gorinych.ui.theme.yellow

@Preview
@Composable
fun FeedbackStars(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        val starsSwitcher = remember {
            mutableStateListOf(0, 0, 0, 0, 0)
        }
        for (index in 0 until 5) {
            SingleStar(
                starsSwitcher = starsSwitcher,
                index = index
            )
        }
    }
}

@Composable
private fun SingleStar(
    starsSwitcher: SnapshotStateList<Int>,
    index: Int,
) {
    IconButton(
        modifier = Modifier.size(24.dp),
        onClick = {
            if (starsSwitcher[index] == 0) {
                calculateCheckedStar(
                    starsSwitcher = starsSwitcher,
                    index = index
                )
            } else {
                calculateCheckedStar(
                    starsSwitcher = starsSwitcher,
                    index = index-1
                )
            }
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_star_rate_24),
            contentDescription = "",
            tint = if (starsSwitcher[index] == 0) white else yellow,
        )
    }
}

private fun calculateCheckedStar(
    starsSwitcher: SnapshotStateList<Int>,
    index: Int,
) {
    starsSwitcher.clear()
    for (i in 0 until index) {
        starsSwitcher[i] = 1
    }
}