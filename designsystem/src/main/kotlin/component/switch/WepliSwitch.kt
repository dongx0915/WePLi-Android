package component.switch

import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import theme.WepliTheme
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WepliSwitch(width: Dp, height: Dp, thumbSize: Dp) {
    // Density -> px 변환 (thumb 이동 거리 계산)
    val density = LocalDensity.current
    val dragRange = with(density) { (width - height).toPx() }

    // Anchors 정의 (왼쪽 = 0, 오른쪽 = 1, 스위치 상태와 위치 매핑)
    val anchors = DraggableAnchors {
        0 at 0f
        1 at dragRange
    }

    val draggableState = remember {
        AnchoredDraggableState(
            initialValue = 0, // 시작 상태는 Off (0)
            anchors = anchors, // 위치 <-> 상태 매핑
            positionalThreshold = { totalDistance: Float -> totalDistance * 0.1f }, // 드래그 가능한 거리 중 몇 % 이상 갔을 때 상태를 바꿀지
            velocityThreshold = { with(density) { 100.dp.toPx() } }, // 드래그 속도가 임계 값을 넘으면 거리가 부족해도 상태 변경됨
            snapAnimationSpec = tween(durationMillis = 300), // 스냅 애니메이션
            decayAnimationSpec = exponentialDecay(), // fling 애니메이션
            confirmValueChange = { true }
        )
    }

    val backgroundModifier = if (draggableState.currentValue == 1) {
        Modifier.background(WepliTheme.color.linear3)
    } else {
        Modifier.background(WepliTheme.color.gray400)
    }

    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(height))
            .then(backgroundModifier)
            .padding(horizontal = 3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clickable {
                    scope.launch {
                        if (draggableState.currentValue == 1) {
                            draggableState.animateTo(0)
                        } else {
                            draggableState.animateTo(1)
                        }
                    }
                }
                .offset {
                    IntOffset(
                        x = draggableState.requireOffset().roundToInt(),
                        y = 0
                    )
                }
                .anchoredDraggable(
                    state = draggableState,
                    orientation = Orientation.Horizontal
                )
                .size(thumbSize)
                .shadow(elevation = 2.dp, shape = CircleShape)
                .clip(CircleShape)
                .background(WepliTheme.color.white)

        )
    }
}