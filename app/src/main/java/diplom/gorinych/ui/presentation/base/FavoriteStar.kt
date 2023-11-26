package diplom.gorinych.ui.presentation.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import diplom.gorinych.R
import diplom.gorinych.domain.utils.IconFavoriteClip

@Composable
fun FavoriteStar(
    modifier: Modifier = Modifier,
    rangDepMax: Int,
) {
    Row (
        modifier = modifier
    ) {
        Box(
            modifier = modifier.size(24.dp)
        ) {
            Image(
                imageVector = ImageVector
                    .vectorResource(id = R.drawable.baseline_star_rate_24_white),
                contentDescription = "",
                modifier = modifier.size(size = 24.dp)
            )
            Image(
                modifier = modifier
                    .size(24.dp)
                    .clip(IconFavoriteClip(rangDepMax.toFloat()/5f)),
                imageVector = ImageVector
                    .vectorResource(id = R.drawable.baseline_star_rate_24_yellow),
                contentDescription = ""
            )
        }
    }
}