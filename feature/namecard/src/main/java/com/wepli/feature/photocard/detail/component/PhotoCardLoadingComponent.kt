package com.wepli.feature.photocard.detail.component

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wepli.feature.photocard.detail.mvi.PhotoCardDetailUiState
import progress.GradientLinearProgressBar
import theme.WepliTheme


@Preview
@Composable
fun PhotoCardLoadingComponentPreview() {
    PhotoCardLoadingComponent(state = PhotoCardDetailUiState())
}

@Composable
fun PhotoCardLoadingComponent(
    state: PhotoCardDetailUiState,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = state.makeCardProgress,
        animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing),
        label = "Animated Progress"
    )

    Column(
        modifier = modifier
            .clickable {  }
            .fillMaxSize()
            .background(
                color = WepliTheme.color.black.copy(alpha = 0.5f)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(3f))
        Text(
            text = "명함을 생성 중입니다",
            style = WepliTheme.typo.title1,
            color = WepliTheme.color.white
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "위플리가 명함을 만드는 중이에요\n조금만 기다리면 근사한 명함을 만들어드릴게요!",
            style = WepliTheme.typo.body4,
            color = WepliTheme.color.gray500,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))
        GradientLinearProgressBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            height = 2.dp,
            progress = animatedProgress
        )

        Text(
            text = "${(animatedProgress * 100).toInt()}%",
            style = WepliTheme.typo.body4,
            color = WepliTheme.color.gray500,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 12.dp)
        )
        Spacer(modifier = Modifier.weight(4f))
    }
}