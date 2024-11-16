package com.wepli.playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.AppBarIcon
import appbar.AppBarIconType
import appbar.ScrollableAppBar
import appbar.WePLiAppBar
import com.wepli.designsystem.R
import com.wepli.playlist.component.ArtistLayout
import com.wepli.playlist.component.PlaylistBsideTrackContent
import com.wepli.playlist.component.PlaylistHeader
import com.wepli.shared.feature.mock.artistMockData

@Preview
@Composable
fun PlaylistScreenPreview() {
    PlaylistScreen(viewModel = hiltViewModel()) {}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistScreen(
    viewModel: PlaylistViewModel = hiltViewModel(),
    navOnBack: () -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    val playlist by rememberUpdatedState(state.playlist)

    ScrollableAppBar(
        scrollState = scrollState,
        backgroundColors = Color.Transparent to Color.Black,
        contentsColors = Color.White to Color.White,
        topBarComponent = { backgroundColor, contentsColor, isFullScrolled ->
            WePLiAppBar(
                title = if (isFullScrolled) playlist.title else "",
                containerColor = backgroundColor,
                contentsColor = contentsColor,
                showBackButton = true,
                actionIcons = listOf {
                    AppBarIcon(
                        icon = AppBarIconType.Like(
                            isLiked = playlist.isLiked,
                            iconColor = { contentsColor },
                            onClick = { viewModel.toggleLiked() }
                        )
                    )
                    AppBarIcon(icon = AppBarIconType.More(iconColor = { contentsColor }))
                },
                onClickBack = { navOnBack() }
            )
        }
    ) { paddingValue ->
        val (topPadding, bottomPadding) = paddingValue.calculateTopPadding() to paddingValue.calculateBottomPadding()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = bottomPadding)
                .verticalScroll(scrollState)
                .background(Color.Black)
        ) {
            // 플레이리스트 정보
            PlaylistHeader(
                state = state,
                modifier = Modifier.padding(top = topPadding)
            )
            Spacer(modifier = Modifier.height(32.dp))

            // 수록곡 목록
            PlaylistBsideTrackContent()

            Spacer(modifier = Modifier.height(20.dp))

            // 참여 아티스트 정보
            ArtistLayout(
                title = "아티스트",
                subscription = "플레이리스트를 빛낸 아티스트들이에요",
                artistList = artistMockData
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}