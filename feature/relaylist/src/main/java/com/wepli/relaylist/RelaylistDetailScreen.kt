package com.wepli.relaylist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import appbar.ScrollableAppBar
import appbar.WepliAppBar
import com.wepli.shared.feature.mock.relaylistMockData
import theme.WepliTheme


@Composable
fun RelaylistDetailScreenRoute() {
    RelaylistDetailScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun RelaylistDetailScreen() {
    val scrollState = rememberLazyListState()
    val relaylist = relaylistMockData.first()

    ScrollableAppBar(
        scrollState = scrollState,
        backgroundColors = Color.Transparent to WepliTheme.color.black,
        contentsColors = Color.White to Color.White,
        topBarComponent = { backgroundColor, contentsColor, isFullScrolled, _ ->
            WepliAppBar(
                title = if (isFullScrolled) "제목" else "",
                containerColor = backgroundColor,
                contentsColor = contentsColor,
                showBackButton = true,
                onClickBack = {  }
            )
        }
    ) { paddingValue ->
        Box {

        }
    }
}