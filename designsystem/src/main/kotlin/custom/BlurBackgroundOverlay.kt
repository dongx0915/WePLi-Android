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
    modifier: Modifier = Modifier,
    @DrawableRes imageResId: Int? = null,
) {
    val gradientBrush = remember {
        val color = Color(0xFF000000)

        Brush.verticalGradient(
            colors = listOf(
                color.copy(alpha = 0.67f),
                color.copy(alpha = 0.70f),
                color.copy(alpha = 0.82f),
                color.copy(alpha = 0.85f),
                color.copy(alpha = 1.0f),
            )
        )
    }

    Box(modifier = modifier.fillMaxWidth()) {
        if (imageResId != null) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .blur(50.dp),
                painter = painterResource(id = imageResId),
                contentDescription = ""
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(brush = gradientBrush)
        )
    }
}
