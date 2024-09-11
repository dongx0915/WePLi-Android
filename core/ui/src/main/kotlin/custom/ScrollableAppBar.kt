package custom

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import kotlin.math.min

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScrollableAppBar(
    title: String = "WePLi",
    scrollState: LazyListState,
    scrollThreshold: Float = 1000f,
    startBgColor: Color = Color.Transparent,
    endBgColor: Color = Color.Black,
    startIconColor: Color = Color.Black,
    endIconColor: Color = Color.White,
    content: @Composable (appbarPadding: PaddingValues) -> Unit
) {
    val totalScrollOffset = rememberCurrentOffset(scrollState).value
    val scrollFraction = min(1f, totalScrollOffset / scrollThreshold).coerceIn(0f, 1f)
    Log.d("ScrollableAppBar", "scrollFraction: $scrollFraction")

    // 배경색과 아이콘 색상 보간
    val backgroundColor = lerp(startBgColor, endBgColor, scrollFraction)
    val iconColor = lerp(startIconColor, endIconColor, scrollFraction)

    val window = (LocalContext.current as? Activity)?.window
    val view = LocalView.current

    // 상태바 아이콘 색상 처리
    DisposableEffect(view, scrollFraction) {
        window?.let {
            WindowCompat.getInsetsController(it, view).apply {
                isAppearanceLightStatusBars = scrollFraction < 0.5f
            }
        }

        onDispose {}
    }

    // Scaffold로 앱바와 콘텐츠 배치
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title, color = iconColor) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                ),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = iconColor
                    )
                }
            )
        },
        content = { paddingValues ->
            content(paddingValues) // 전달받은 콘텐츠 배치
        }
    )
}

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

/**
 *
 * @param title 앱바 타이틀
 * @param scrollThreshold 스크롤 임계값
 * @param startBgColor 배경 초기 색상
 * @param endBgColor 스크롤 후 배경색
 * @param startIconColor 초기 아이콘 색상
 * @param endIconColor 스크롤 후 아이콘 색상
 * @param content 재사용 가능한 콘텐츠
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScrollableAppBar(
    title: String = "WePLi",
    scrollState: ScrollState,
    scrollThreshold: Float = 600f,
    startBgColor: Color = Color.Transparent,
    endBgColor: Color = Color.Black,
    startIconColor: Color = Color.Black,
    endIconColor: Color = Color.White,
    content: @Composable (appbarPadding: PaddingValues) -> Unit
) {
    // 스크롤의 진행 비율을 0에서 1 사이로 계산 (외부에서 받은 scrollThreshold 값 사용)
    val scrollFraction = (scrollState.value / scrollThreshold).coerceIn(0f, 1f)
    Log.d("ScrollableAppBar", "scrollFraction: $scrollFraction")
    // 배경색을 외부에서 받은 색상으로 선형 보간
    val backgroundColor = lerp(startBgColor, endBgColor, scrollFraction)
    val iconColor = lerp(startIconColor, endIconColor, scrollFraction)
    val window = (LocalContext.current as? Activity)?.window
    val view = LocalView.current

    // 시스템 상단바 아이콘 색상 변경 처리
    DisposableEffect(view, scrollFraction) {
        window ?: return@DisposableEffect onDispose {}
        WindowCompat.getInsetsController(window, view).apply {
            // 스크롤 진행 상태에 따라 상태바의 아이콘 색상 변경
            isAppearanceLightStatusBars = !(scrollFraction > 0.5f)
        }

        onDispose {}
    }

    // Scaffold는 Jetpack Compose의 레이아웃을 구성하는 기본 구조로, topBar와 content를 설정
    Scaffold(
        // 상단 앱바를 구성하는 topBar 설정
        topBar = {
            TopAppBar(
                title = { Text(title, color = iconColor) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                ),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = iconColor
                    )
                }
            )
        },
        // 컨텐츠를 외부에서 전달받아 배치
        content = { paddingValue ->
            content(paddingValue) // 외부에서 전달된 콘텐츠를 표시
        }
    )
}
