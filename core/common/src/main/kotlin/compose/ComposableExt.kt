package compose

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit

@Composable
fun Dp.toPx(): Int = with(LocalDensity.current) { this@toPx.roundToPx() }

@Composable
fun Dp.toSp(): TextUnit = with(LocalDensity.current) { this@toSp.toSp() }

// 페이지 오프셋을 계산하는 확장 함수
fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    // 현재 페이지와 대상 페이지의 차이에 현재 페이지의 오프셋을 더해서 반환
    return (currentPage - page) + currentPageOffsetFraction
}

@Composable
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier {
    return this.then(
        Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) { onClick() }
    )
}

// 터치(제스처) 비활성화
fun Modifier.gesturesDisabled(disabled: Boolean = true): Modifier {
    return if (disabled) {
        pointerInput(Unit) {
            awaitPointerEventScope {
                // 모든 새로운 포인터 이벤트를 대기
                while (true) {
                    // 이벤트를 소비하여 다른 동작을 막음
                    awaitPointerEvent(pass = PointerEventPass.Initial)
                        .changes
                        .forEach(PointerInputChange::consume)
                }
            }
        }
    } else {
        this
    }
}

fun Modifier.shimmerEffect(radius: Dp, duration: Int, delay: Int): Modifier = composed {
    val baseColor = Color.LightGray
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(duration, delay),
        ),
        label = ""
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                baseColor.copy(alpha = 0.1f),
                baseColor.copy(alpha = 0.6f),
                baseColor.copy(alpha = 0.1f),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat() / 2)
        ),
        shape = RoundedCornerShape(radius)
    ).onGloballyPositioned { size = it.size }
}

// Skeleton 효과
fun Modifier.shimmerEffect(radius: Dp): Modifier = shimmerEffect(radius, 1000, 0)

fun Modifier.topBorder(
    brush: Brush,
    height: Float,
    alpha: Float = 1f,
) = this.drawWithContent {
    drawContent()
    drawLine(
        brush = brush,
        start = Offset(0f, 0f),
        end = Offset(size.width, 0f),
        strokeWidth = height,
        alpha = alpha,
    )
}

fun Modifier.rightBorder(
    brush: Brush,
    width: Float,
    alpha: Float = 1f,
) = this.drawWithContent {
    drawContent()
    drawLine(
        brush = brush,
        start = Offset(size.width, 0f),
        end = Offset(size.width, size.height),
        strokeWidth = width,
        alpha = alpha,
    )
}

fun Modifier.bottomBorder(
    brush: Brush,
    height: Float,
    alpha: Float = 1f,
) = this.drawWithContent {
    drawContent()
    drawLine(
        brush = brush,
        start = Offset(0f, size.height),
        end = Offset(size.width, size.height),
        strokeWidth = height,
        alpha = alpha,
    )
}

fun Modifier.leftBorder(
    brush: Brush,
    width: Float,
    alpha: Float = 1f,
) = this.drawWithContent {
    drawContent()
    drawLine(
        brush = brush,
        start = Offset(0f, 0f),
        end = Offset(0f, size.height),
        strokeWidth = width,
        alpha = alpha,
    )
}

fun Modifier.topBorderWithRoundedCorners(
    brush: Brush,
    height: Dp,
    cornerRadius: Dp,
    alpha: Float = 1f,
): Modifier = this.drawWithContent {
    val strokeWidth = height.toPx()
    val radius = cornerRadius.toPx()
    val path = Path().apply {
        // 좌측 상단 코너
        arcTo(
            rect = Rect(
                left = 0f,
                top = strokeWidth / 2,
                right = 2 * radius,
                bottom = strokeWidth / 2 + 2 * radius
            ),
            startAngleDegrees = 180f, // 호의 시작점 (0도 : 오른쪽, 90도 : 아래쪽)
            sweepAngleDegrees = 90f, // 호가 그려지는 방향과 각도 (90f이면 시계방향으로 90도 회전하면서 그려짐)
            forceMoveTo = false
        )
1
        // Draw the top straight line (between the rounded corners)
        lineTo(size.width - radius, strokeWidth / 2)

        // 오른쪽 상단 코너
        arcTo(
            rect = Rect(
                left = size.width - 2 * radius,
                top = strokeWidth / 2,
                right = size.width,
                bottom = strokeWidth / 2 + 2 * radius
            ),
            startAngleDegrees = -90f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )
    }

    drawContent()
    drawPath(
        path = path,
        brush = brush,
        style = Stroke(width = strokeWidth),
        alpha = alpha
    )
}