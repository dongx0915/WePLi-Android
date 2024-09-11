package compose

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * LazyList의 현재 총 스크롤 오프셋을 기억하고 계산하는 함수
 *
 * LazyList의 첫 번째 보이는 항목의 인덱스와 해당 항목의 스크롤 오프셋을 추적
 * 이전 항목 인덱스 및 오프셋과 비교하여, 리스트가 위 또는 아래로 스크롤되는지에 따라
 * 총 스크롤 오프셋을 조정
 *
 * @return [State] 현재 총 스크롤 오프셋[Int]
 */
@Composable
fun rememberCurrentOffset(state: LazyListState): State<Int> {
    val position =  remember { derivedStateOf { state.firstVisibleItemIndex } }
    val itemOffset = remember { derivedStateOf { state.firstVisibleItemScrollOffset } }
    val lastPosition = rememberPrevious(position.value)
    val lastItemOffset = rememberPrevious(itemOffset.value)
    val currentOffset = remember { mutableIntStateOf(0) }

    LaunchedEffect(position.value, itemOffset.value) {
        if (lastPosition == null || position.value == 0) {
            currentOffset.intValue = itemOffset.value
        } else if (lastPosition == position.value) {
            currentOffset.intValue += (itemOffset.value - (lastItemOffset ?: 0))
        } else if (lastPosition > position.value) {
            currentOffset.intValue -= (lastItemOffset ?: 0)
        } else { // lastPosition.value < position.value
            currentOffset.intValue += itemOffset.value
        }
    }

    return currentOffset
}

/**
 * 주어진 입력 값의 이전 상태를 기억하고, 현재 값이 변경될 때만 업데이트되는 함수.
 *
 * Composable 내에서 값의 이전 상태를 추적하기 위해 사용
 * 현재 값이 이전 값과 다른지 확인하고, 다를 경우 이전 값을 업데이트
 *
 * @param current 추적할 현재 값.
 * @param shouldUpdate 이전 값과 현재 값을 비교하여 다를 경우 true 반환
 * 기본적으로는 참조가 다른 경우 업데이트
 * @return 업데이트 전의 이전 값, 이전 값이 없을 경우 null을 반환.
 */
@Composable
fun <T> rememberPrevious(
    current: T,
    shouldUpdate: (prev: T?, curr: T) -> Boolean = { a: T?, b: T -> a != b },
): T? {
    val ref = remember { mutableStateOf<T?>(null) }
    SideEffect {
        if (shouldUpdate(ref.value, current)) {
            ref.value = current
        }
    }

    return ref.value
}
