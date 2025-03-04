package com.wepli.relaylist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import appbar.ScrollableAppBar
import appbar.WepliAppBar
import com.wepli.designsystem.R
import com.wepli.shared.feature.mock.relaylistUiMockData
import image.AsyncImageWithPreview
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
    val relaylist = relaylistUiMockData.random()

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
        Box(modifier = Modifier
            .fillMaxSize()
            .background(WepliTheme.color.black)) {

            // 백그라운드
            AsyncImageWithPreview(
                imageUrl = relaylist.coverImgUrl,
                contentScale = ContentScale.Fit,
                previewImage = painterResource(id = R.drawable.img_placeholder_chuu_3),
                modifier = Modifier
                    .fillMaxSize()
                    .blur(20.dp),
            )

            // 릴레이리스트 콘텐츠
            Column {

            }
        }
    }
}