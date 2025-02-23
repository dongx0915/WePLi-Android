package com.wepli.feature.photocard.main

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.WepliAppBar
import button.WepliBasicButton
import button.WepliButtonStyle
import com.wepli.feature.photocard.component.PhotoCardComponent4
import com.wepli.feature.photocard.main.mvi.PhotoCardMainUiState
import com.wepli.feature.photocard.main.viewmodel.PhotoCardMainViewModel
import extensions.compose.shimmerEffect
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.compose.collectAsState
import theme.WepliTheme

@Composable
fun PhotoCardScreenRoute(
    navOnBack: () -> Unit,
    navOnPhotoCardDetail: () -> Unit,
) {
    val viewModel: PhotoCardMainViewModel = hiltViewModel()
    val state by viewModel.collectAsState()

    PhotoCardScreen(
        state = state,
        navOnBack = navOnBack,
        navOnPhotoCardDetail = navOnPhotoCardDetail
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PhotoCardScreen(
    state: PhotoCardMainUiState,
    navOnBack: () -> Unit,
    navOnPhotoCardDetail: () -> Unit,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f, // 시작 오프셋
        targetValue = 25f, // 이동할 최대 오프셋 (아래 예시는 15f만큼 이동)
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 2000, // 위아래 왕복에 걸리는 시간
            ),
            repeatMode = RepeatMode.Reverse // 왕복 애니메이션
        ),
        label = "FloatUpDown"
    )

    // 각 섹션을 순차적으로 보여주기 위한 visible 상태
    val isInPreview = LocalInspectionMode.current
    var section1Visible by remember { mutableStateOf(isInPreview) }
    var section2Visible by remember { mutableStateOf(isInPreview) }

    LaunchedEffect(Unit) {
        delay(300)
        section1Visible = true
        delay(300)
        section2Visible = true
    }

    Scaffold(
        topBar = {
            WepliAppBar(
                title = "",
                showBackButton = true,
                onClickBack = { navOnBack() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = WepliTheme.color.black)
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .padding(bottom = 20.dp)
        ) {
            Spacer(modifier = Modifier.padding(top = 56.dp))
            // Section 1
            AnimatedContent(section1Visible) {
                Text(
                    text = "내 취향 명함 만들기",
                    style = WepliTheme.typo.title1,
                    color = WepliTheme.color.gray900
                )
                Spacer(modifier = Modifier.padding(top = 12.dp))
                Text(
                    text = "${state.user.nickname}님의 취향이 드러나는 명함을 만들어드려요",
                    style = WepliTheme.typo.body4,
                    color = WepliTheme.color.gray500
                )
            }

            // Section 2
            AnimatedContent(
                isVisible = section2Visible,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            translationY = offsetY
                        }
                        .scale(0.9f)
                        .align(Alignment.CenterHorizontally)
                ) {
                    PhotoCardComponent4(
                        photoCardInfo = state.photoCardInfo,
                        isEnabledShimmer = true
                    )

                    Box(
                        modifier = Modifier.shimmerEffect(12.dp, 2000, 1500)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                WepliBasicButton(
                    title = "시작하기",
                    isEnabled = true,
                    onClick = { navOnPhotoCardDetail() },
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .align(Alignment.CenterHorizontally),
                    buttonStyle = WepliButtonStyle.Basic,
                )
            }
        }
    }
}

@Composable
private fun AnimatedContent(isVisible: Boolean, modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { it }
        ) + fadeIn(animationSpec = tween(700)),
        exit = fadeOut()
    ) {
        Column {
            content()
        }
    }
}

@Preview
@Composable
fun PhotoCardMainScreenPreview() {
    PhotoCardScreen(
        state = PhotoCardMainUiState(),
        navOnBack = { },
        navOnPhotoCardDetail = { }
    )
}