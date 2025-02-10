package com.wepli.feature.namecard.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import progress.GradientLinearProgressBar
import theme.WepliTheme


@Preview
@Composable
fun NameCardLoadingComponent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = WepliTheme.color.black.copy(alpha = 0.7f)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
            height = 1.5.dp,
            progress = 0.3f
        )

        Text(
            text = "30%",
            style = WepliTheme.typo.body4,
            color = WepliTheme.color.gray500,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}