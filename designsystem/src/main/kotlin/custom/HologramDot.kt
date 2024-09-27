package custom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NotificationDot(
    modifier: Modifier = Modifier,
) {
    val gradientColors = listOf(
        Color(0xFFB0C6FF) to 0.0f,
        Color(0xFFEEF4E1) to 0.15f,
        Color(0xFF71FFF6) to 0.22f,
        Color(0xFFFAFCFE) to 0.30f,
        Color(0xFFDCCCFF) to 0.45f,
        Color(0xFFC0D1F9) to 0.50f,
        Color(0xFFBFD1F9) to 0.54f,
        Color(0xFFFAFCFE) to 0.75f,
        Color(0xFF71FFF6) to 0.85f,
        Color(0xFFB6D0F7) to 1.0f,
    )

    val colors = gradientColors.map { it.first }
    val colorStops = gradientColors.map { it.second }

    Canvas(modifier = modifier) {
        drawIntoCanvas { canvas ->
            val sweepGradient = SweepGradientShader(
                center = Offset(size.width / 2, size.height / 2),
                colors = colors,
                colorStops = colorStops
            )

            val paint = Paint().apply {
                shader = sweepGradient
            }

            canvas.drawCircle(
                center = Offset(size.width / 2, size.height / 2),
                radius = size.minDimension / 2,
                paint = paint
            )
        }
    }
}

@Preview
@Composable
fun PreviewAngularGradientCircle() {
    NotificationDot(
        modifier = Modifier.size(200.dp)
    )
}
