package diplom.gorinych.domain.utils

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class IconFavoriteClip(private val currentRating: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            addRect(
                Rect(
                    left = 0f,
                    top = 0f,
                    right = size.width * currentRating,
                    bottom = size.height
                )
            )
        }
        return Outline.Generic(path = path)
    }
}