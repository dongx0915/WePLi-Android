package com.wepli.devmode.fcm.presentation.component

import androidx.compose.animation.core.spring
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

enum class LimitedDismissValue { Default, DismissedEndToStart }

/**
 * EndToStart(오른쪽 -> 왼쪽)으로만 100dp(기본)까지 드래그 가능.
 * 그 이상은 멈추며, 임계치를 넘겨 놓으면 Dismissed로 스냅.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LimitedSwipeToDismissBox(
    modifier: Modifier = Modifier,
    maxOffsetDp: Dp = 100.dp,
    positionalThresholdFraction: Float = 0.25f, // 스냅 임계치(최대 거리의 25%)
    velocityThreshold: Dp = 200.dp,             // 빠른 플릭 스냅 기준
    backgroundContent: @Composable BoxScope.() -> Unit,
    content: @Composable BoxScope.(dismissed: Boolean) -> Unit,
    onDismissed: () -> Unit = {}
) {
    val density = LocalDensity.current
    val layoutDir = LocalLayoutDirection.current

    val maxOffsetPx = with(density) { maxOffsetDp.toPx() }
    val velocityThresholdPx = with(density) { velocityThreshold.toPx() }

    // LTR/RTL 부호 처리된 anchors
    val anchors = remember(maxOffsetPx, layoutDir) {
        val endToStartOffset =
            if (layoutDir == LayoutDirection.Ltr) -maxOffsetPx else +maxOffsetPx

        DraggableAnchors {
            LimitedDismissValue.Default at 0f
            LimitedDismissValue.DismissedEndToStart at endToStartOffset
        }
    }
    val decay = rememberSplineBasedDecay<Float>()

    val state = remember {
        AnchoredDraggableState(
            initialValue = LimitedDismissValue.Default,
            positionalThreshold = { distance: Float -> distance * positionalThresholdFraction },
            velocityThreshold = { velocityThresholdPx },
            decayAnimationSpec = decay,
            confirmValueChange = { true },
            anchors = anchors,
            snapAnimationSpec = spring(),
        )
    }

    // Anchors (레이아웃/크기에 영향받지 않지만 안전하게 갱신)
    LaunchedEffect(anchors) { state.updateAnchors(anchors) }

    // Dismissed 상태 도달 시 콜백
    LaunchedEffect(state.currentValue) {
        if (state.currentValue == LimitedDismissValue.DismissedEndToStart) {
            onDismissed()
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            // 수평 드래그 활성화
            .anchoredDraggable(
                state = state,
                orientation = Orientation.Horizontal
            )
    ) {
        // Background (오른쪽에 삭제 아이콘 등)
        backgroundContent()

        // Foreground: 오프셋을 직접 적용 (최대 100dp로 클램프)
        val offsetX = state.requireOffset().roundToInt()
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX, 0) }
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            content(state.currentValue == LimitedDismissValue.DismissedEndToStart)
        }
    }
}