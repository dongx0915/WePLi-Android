package base

import android.util.Log

interface UiState {
    fun toLog() = Log.i("EapState", "State Changed: $this")
}

/**
 * Event: 사용자의 액션이나 UI 변경을 유발하는 동작
 * - View -> ViewModel을 호출하던 것들
 * - View -> (Event) -> Dispatcher -> ViewModel -> (UiState, SideEffect) -> View
 */
interface Intent {
    fun toLog() = Log.i("EapState", "Event Received: $this")
}

/**
 * SideEffect: 이벤트를 처리한 결과로 발생해야 하는 부가적인 작업
 * - ex) ViewModel -> View로 전달해야 하는 이벤트,
 *       UI에 일회성으로 발생하는 작업
 */
interface SideEffect {
    fun toLog() = Log.i("EapState", "SideEffect Posted: $this")
    data class DefaultException(val type: Int) : SideEffect
}