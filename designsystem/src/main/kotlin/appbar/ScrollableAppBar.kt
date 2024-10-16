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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import compose.rememberCurrentOffset
import kotlin.math.min

/**
 * @param scrollThreshold 스크롤 임계값
 * @param backgroundColors 배경색
 * @param contentsColors 콘텐츠 색상
 * @param content 재사용 가능한 콘텐츠
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun ScrollableAppBar(
    scrollState: LazyListState,
    scrollThreshold: Float = 1000f,
    backgroundColors: Pair<Color, Color> = Color.Transparent to Color.Black,
    contentsColors: Pair<Color, Color> = Color.Black to Color.White,
    topBarComponent: @Composable (backgroundColor: Color, contentsColor: Color, isFullScrolled: Boolean) -> Unit,
    content: @Composable (appbarPadding: PaddingValues) -> Unit,
) {
    val totalScrollOffset = rememberCurrentOffset(scrollState)
    val scrollFraction = min(1f, totalScrollOffset.value / scrollThreshold).coerceIn(0f, 1f)
    Log.d("ScrollableAppBar", "scrollFraction: $scrollFraction")

    // 배경색과 아이콘 색상 보간
    val backgroundColor = lerp(backgroundColors.first, backgroundColors.second, scrollFraction)
    val contentsColor = lerp(contentsColors.first, contentsColors.second, scrollFraction)
    val isFullScrolled = derivedStateOf { scrollFraction >= 1f }
    val window = (LocalContext.current as? Activity)?.window
    val view = LocalView.current

    // 상태바 아이콘 색상 처리
    LaunchedEffect(contentsColor) {
        window?.let {
            // contentsColor의 밝기를 기반으로 상태바 아이콘 색상 결정
            val isContentColorLight = contentsColor.luminance() > 0.5f
            WindowCompat.getInsetsController(it, view).isAppearanceLightStatusBars = !isContentColorLight
        }
    }

    // Scaffold로 앱바와 콘텐츠 배치
    Scaffold(
        topBar = { topBarComponent(backgroundColor, contentsColor, isFullScrolled.value) },
        content = { paddingValues -> content(paddingValues) }
    )
}

/**
 * @param scrollThreshold 스크롤 임계값
 * @param backgroundColors 배경색
 * @param contentsColors 콘텐츠 색상
 * @param content 재사용 가능한 콘텐츠
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun ScrollableAppBar(
    scrollState: ScrollState,
    scrollThreshold: Float = 1000f,
    backgroundColors: Pair<Color, Color> = Color.Transparent to Color.Black,
    contentsColors: Pair<Color, Color> = Color.Black to Color.White,
    topBarComponent: @Composable (backgroundColor: Color, contentsColor: Color, isFullScrolled: Boolean) -> Unit,
    content: @Composable (appbarPadding: PaddingValues) -> Unit,
) {
    // 스크롤의 진행 비율을 0에서 1 사이로 계산 (외부에서 받은 scrollThreshold 값 사용)
    val scrollFraction = (scrollState.value / scrollThreshold).coerceIn(0f, 1f)
    Log.d("ScrollableAppBar", "scrollFraction: $scrollFraction")

    // 배경색을 외부에서 받은 색상으로 선형 보간
    val backgroundColor = lerp(backgroundColors.first, backgroundColors.second, scrollFraction)
    val contentsColor = lerp(contentsColors.first, contentsColors.second, scrollFraction)
    val isFullScrolled = derivedStateOf { scrollFraction >= 1f }
    val window = (LocalContext.current as? Activity)?.window
    val view = LocalView.current

    // 상태바 아이콘 색상 처리
    LaunchedEffect(contentsColor) {
        window?.let {
            // contentsColor의 밝기를 기반으로 상태바 아이콘 색상 결정
            val isContentColorLight = contentsColor.luminance() > 0.5f
            WindowCompat.getInsetsController(it, view).isAppearanceLightStatusBars = !isContentColorLight
        }
    }

    // Scaffold는 Jetpack Compose의 레이아웃을 구성하는 기본 구조로, topBar와 content를 설정
    Scaffold(
        topBar = { topBarComponent(backgroundColor, contentsColor, isFullScrolled.value) },
        content = { paddingValue -> content(paddingValue) }
    )
}

/* Preview */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyScrollableAppBar() {
    val scrollState = rememberLazyListState()
    ScrollableAppBar(
        scrollState = scrollState,
        backgroundColors = Color.Transparent to Color.Black,
        contentsColors = Color.Black to Color.White,
        topBarComponent = { backgroundColor, iconColor, _ ->
            WePLiAppBar(
                containerColor = backgroundColor,
                contentsColor = iconColor,
                showBackButton = true,
            )
        },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScrollableAppBar() {
    val scrollState = rememberScrollState()
    ScrollableAppBar(
        scrollState = scrollState,
        backgroundColors = Color.Transparent to Color.Black,
        contentsColors = Color.Black to Color.White,
        topBarComponent = { backgroundColor, iconColor, _ ->
            WePLiAppBar(
                containerColor = backgroundColor,
                contentsColor = iconColor,
                showBackButton = true,
            )
        },
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