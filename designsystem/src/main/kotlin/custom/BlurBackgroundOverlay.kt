package custom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun BlurBackgroundOverlay(
    backgroundColor: Color = Color.Black,
    colorStops: List<Float> = listOf(0.67f, 0.70f, 0.82f, 0.85f, 1.0f),
    modifier: Modifier = Modifier,
    blurModifier: Modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1f),
    imageModifier: Modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1f),
    @DrawableRes imageResId: Int? = null,
) {
    val gradientBrush = remember {
        Brush.verticalGradient(
            colors = colorStops.map { alpha ->
                backgroundColor.copy(alpha = alpha)
            }
        )
    }

    Box(modifier = modifier.fillMaxWidth()) {
        if (imageResId != null) {
            Image(
                modifier = imageModifier.blur(50.dp),
                painter = painterResource(id = imageResId),
                contentDescription = ""
            )
        }

        Box(modifier = blurModifier.background(brush = gradientBrush))
    }
}