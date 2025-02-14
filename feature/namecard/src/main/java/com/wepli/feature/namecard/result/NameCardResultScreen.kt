package com.wepli.feature.namecard.result

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import appbar.AppBarIcon
import appbar.AppBarIconType
import appbar.WepliAppBar
import button.WepliBasicButton
import button.WepliButtonStyle
import theme.WepliTheme

@Composable
fun NameCardResultScreenRoute(navOnBack: () -> Unit) {
    NameCardResultScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NameCardResultScreen() {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            WepliAppBar(
                title = "",
                showBackButton = true,
                actionIcons = listOf {
                    AppBarIcon(icon = AppBarIconType.Save())
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(color = WepliTheme.color.black)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(paddingValues)
                .padding(top = 56.dp, start = 24.dp, end = 24.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "명함이 완성되었어요!",
                style = WepliTheme.typo.title1,
                color = WepliTheme.color.gray900
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "oo님만의 명함이 완성 되었어요!\n친구들에게 공유해볼까요?",
                style = WepliTheme.typo.body4,
                color = WepliTheme.color.gray500,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.weight(4f))
            Card(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .widthIn(max = 247.dp)
                    .heightIn(max = 354.dp)
                    .aspectRatio(247f / 354f)
                    .align(Alignment.CenterHorizontally)
            ) { }

            Spacer(modifier = Modifier.weight(3f))
            WepliBasicButton(
                title = "공유하기",
                isEnabled = true,
                onClick = { },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                buttonStyle = WepliButtonStyle.Basic,
            )
            Spacer(modifier = Modifier.height(8.dp))
            WepliBasicButton(
                title = "나가기",
                isEnabled = true,
                onClick = { },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                buttonStyle = WepliButtonStyle.Transparent,
            )
        }
    }
}