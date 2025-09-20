package compose

import androidx.compose.foundation.pager.PagerState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlin.math.abs
import kotlin.math.absoluteValue


// 페이드 전환 효과를 적용하는 Modifier 확장 함수
fun Modifier.pagerFadeTransition(pagerState: PagerState, page: Int): Modifier {
    return graphicsLayer {
        val pageOffset = pagerState.calculateCurrentOffsetForPage(page)
        // 페이지 크기에 따라 콘텐츠를 이동시켜 가운데 유지
        translationX = pageOffset * size.width
        // alpha 값을 적용하여 페이드 효과 추가
        alpha = (1 - pageOffset.absoluteValue)
    }
}

fun Modifier.pagerZoomOut(
    state: PagerState,
    page: Int,
    minScale: Float = 0.92f,
    alphaStrength: Float = 1f,
): Modifier = graphicsLayer {
    fun easeOutCubic(x: Float) = 1f - (1f - x) * (1f - x) * (1f - x)

    val offset = state.calculateCurrentOffsetForPage(page)
    val t = easeOutCubic(abs(offset).coerceIn(0f, 1f))
    val scale = androidx.compose.ui.util.lerp(1f, minScale, t)

    translationX = offset * size.width
    scaleX = scale
    scaleY = scale
    alpha = 1f - t * alphaStrength
}
