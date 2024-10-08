package appbar

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    val totalScrollOffset = remember {
        derivedStateOf {
            val firstItemIndex = scrollState.firstVisibleItemIndex
            val firstOffset = scrollState.firstVisibleItemScrollOffset

            firstItemIndex * 100 + firstOffset
        }
    }
    val scrollFraction = min(1f, totalScrollOffset.value / scrollThreshold).coerceIn(0f, 1f)
    Log.d("ScrollableAppBar", "scrollFraction: $scrollFraction")

    // 배경색과 아이콘 색상 보간
    val backgroundColor = lerp(startBgColor, endBgColor, scrollFraction)
    val iconColor = lerp(startIconColor, endIconColor, scrollFraction)

    val window = (LocalContext.current as? Activity)?.window
    val view = LocalView.current

    // 상태바 아이콘 색상 처리
    DisposableEffect(view, scrollFraction) {
        window?.let {
            WindowCompat.getInsetsController(it, view).isAppearanceLightStatusBars = scrollFraction < 0.5f
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

/* Preview */
@Composable
fun LazyScrollableAppBar() {
    val scrollState = rememberLazyListState()
    ScrollableAppBar(
        scrollState = scrollState,
        startBgColor = Color.Transparent,
        endBgColor = Color.Black,
        content = { paddingValue ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValue),
                state = scrollState
            ) {
                items(100) {
                    Text(
                        text = "Item $it",
                        color = Color.Black,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun ScrollableAppBar() {
    val scrollState = rememberScrollState()
    ScrollableAppBar(
        title = "WePLi",
        scrollState = scrollState,
        content = { paddingValue ->
            Column(
                modifier = Modifier
                    .padding(paddingValue)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                repeat(100) {
                    Text(
                        text = "Item $it",
                        color = Color.Black,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun LazyScrollableAppBarPreview() {
    LazyScrollableAppBar()
}

@Preview
@Composable
fun ScrollableAppBarPreview() {
    ScrollableAppBar()
}