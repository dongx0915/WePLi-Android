package compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout

/**
 * @property Measured 높이를 측정하기 위한 슬록
 * @property Content 측정한 높이를 사용할 콘텐츠용 슬롯
 */
enum class SlotId {
    Measured,
    Content
}

/**
 *  LazyRow의 높이를 아이템의 최대 높이로 고정하기 위한 컴포저블
 *
 *  @param measured 측정하고 싶은 높이를 갖는 Composable
 *  @param content 측정한 높이를 높이로 갖는 Composable
 */
@Composable
fun MeasuredHeightContainer(
    modifier: Modifier = Modifier,
    measured: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    SubcomposeLayout(modifier = modifier) { constraints ->
        // 최소 너비 0으로 설정
        val wrappedConstraints = constraints.copy(minWidth = 0)

        // measured 컴포저블을 한 번만 서브 컴포지션(subcompose)하고 이를 측정
        // 여기서 얻은 높이를 이후 content 컴포저블의 고정 높이로 사용
        val measuredHeight = subcompose(SlotId.Measured, measured).first().measure(wrappedConstraints).height

        val fixedHeightConstraints = wrappedConstraints.copy(
            minHeight = measuredHeight,
            maxHeight = measuredHeight
        )

        // content 컴포저블을 측정하여 placeables 리스트에 저장
        val contentPlaceables: List<Placeable> = subcompose(SlotId.Content, content).map {
            it.measure(fixedHeightConstraints)
        }

        // 레이아웃 너비 계산
        // 고정된 너비로 설정된 경우 그 값을 사용, 그렇지 않으면 자식 콘텐츠들의 너비 합산
        val layoutWidth = when {
            constraints.hasFixedWidth -> constraints.maxWidth // 고정 너비인 경우
            constraints.hasBoundedWidth -> constraints.maxWidth.coerceAtMost(contentPlaceables.sumOf { it.width }) // 범위로 설정된 경우
            else -> contentPlaceables.sumOf { it.width }.coerceIn(constraints.minWidth, constraints.maxWidth)
        }

        // 콘텐츠를 가로로 배치
        layout(layoutWidth, measuredHeight) {
            var xPos = 0
            contentPlaceables.forEach { placeable ->
                placeable.placeRelative(xPos, 0)
                xPos += placeable.width
            }
        }
    }
}