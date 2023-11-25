package diplom.gorinych.ui.presentation.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import diplom.gorinych.R
import diplom.gorinych.ui.theme.white
import diplom.gorinych.ui.theme.yellow

@Composable
fun FeedbackStars(
    modifier: Modifier = Modifier,
    starsSwitcher: MutableState<List<Boolean>>,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {


        for (index in 0..4) {
            SingleStar(
                starsSwitcher = starsSwitcher,
                index = index
            )
        }
    }
}

@Composable
private fun SingleStar(
    starsSwitcher: MutableState<List<Boolean>>,
    index: Int,
) {
    IconButton(
        modifier = Modifier.size(24.dp),
        onClick = {
            starsSwitcher.value = if (!starsSwitcher.value[index]) {
                calculateCheckedStar(
                    index = index
                )
            } else {
                calculateCheckedStar(
                    index = index - 1
                )
            }
        }
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_star_rate_24),
            contentDescription = "",
            tint = if (!starsSwitcher.value[index]) white else yellow,
        )
    }
}

fun calculateCheckedStar(
    index: Int,
): List<Boolean> {
    val starsSwitcher = mutableListOf(false, false, false, false, false)
    for (i in 0..index) {
        starsSwitcher.apply {
            this[i] = true
        }.toList()
    }
    return starsSwitcher
}
