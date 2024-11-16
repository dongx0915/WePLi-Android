package extensions.compose

import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import kotlin.math.absoluteValue

@Composable
fun Dp.toPx(): Int = with(LocalDensity.current) { this@toPx.roundToPx() }

@Composable
fun Dp.toSp(): TextUnit = with(LocalDensity.current) { this@toSp.toSp() }

// 페이지 오프셋을 계산하는 확장 함수
fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    // 현재 페이지와 대상 페이지의 차이에 현재 페이지의 오프셋을 더해서 반환
    return (currentPage - page) + currentPageOffsetFraction
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

// 페이드 전환 효과를 적용하는 Modifier 확장 함수
fun Modifier.pagerFadeTransition(page: Int, pagerState: PagerState): Modifier {
    return graphicsLayer {
        val pageOffset = pagerState.calculateCurrentOffsetForPage(page)
        // 페이지 크기에 따라 콘텐츠를 이동시켜 가운데 유지
        translationX = pageOffset * size.width
        // alpha 값을 적용하여 페이드 효과 추가
        alpha = 1 - pageOffset.absoluteValue
    }
}