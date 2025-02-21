package progress

import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import theme.WepliTheme

@Composable
fun GradientLinearProgressBar(
    @FloatRange(0.0, 1.0) progress: Float,
    progressColor: Brush = WepliTheme.color.linear3,
    trackColor: Color = WepliTheme.color.gray100,
    modifier: Modifier = Modifier,
    height: Dp,
    cornerRadius: Dp = 4.dp
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        val progressWidth = size.width * progress

        // 배경 (트랙) 그리기
        drawRoundRect(
            color = trackColor,
            size = Size(size.width, size.height),
            cornerRadius = CornerRadius(cornerRadius.toPx())
        )

        // 진행 바 그리기
        drawRoundRect(
            brush = progressColor,
            size = Size(progressWidth, size.height),
            cornerRadius = CornerRadius(cornerRadius.toPx())
        )
    }
}