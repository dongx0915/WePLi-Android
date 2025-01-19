package com.wepli.playlist

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.PlaylistAppBar
import com.wepli.playlist.component.ArtistLayout
import com.wepli.playlist.component.PlaylistBsideTrackContent
import com.wepli.playlist.component.PlaylistHeader
import com.wepli.playlist.mvi.PlaylistEffect
import com.wepli.playlist.mvi.PlaylistIntent
import com.wepli.shared.feature.mock.artistMockData
import com.wepli.shared.feature.mock.playlistMockData
import com.wepli.shared.feature.uimodel.playlist.PlaylistUiData
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Preview
@Composable
fun PlaylistScreenPreview() {
    PlaylistScreen(
        playlist = PlaylistUiData.fromDomain(playlistMockData.random()),
        sendAction = {},
        navOnBack = {}
    )
}

@Composable
fun PlaylistScreenRoute(
    navOnBack: () -> Unit,
) {
    val viewModel: PlaylistViewModel = hiltViewModel()
    val state by viewModel.collectAsState()
    val context = LocalContext.current

    viewModel.collectSideEffect { effect ->
        when (effect) {
            is PlaylistEffect.PlaylistFetchError -> {
                Toast.makeText(context, "플레이리스트 조회에 실패하였습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    PlaylistScreen(
        navOnBack = { navOnBack() },
        sendAction = { viewModel.processIntent(it) },
        playlist = state.playlist,
    )
}

@Composable
fun PlaylistScreen(
    playlist: PlaylistUiData,
    sendAction: (PlaylistIntent) -> Unit,
    navOnBack: () -> Unit,
) {
    PlaylistAppBar(
        playlistTitle = playlist.title,
        playlistIsLiked = playlist.isLiked,
        onClickLike = { sendAction(PlaylistIntent.OnClickLike) },
        navOnBack = navOnBack
    ) { scrollState, paddingValue ->
        val (topPadding, bottomPadding) = paddingValue.calculateTopPadding() to paddingValue.calculateBottomPadding()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = bottomPadding)
                .verticalScroll(scrollState)
                .background(Color.Black),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // 플레이리스트 정보
            PlaylistHeader(
                playlist = playlist,
                modifier = Modifier.padding(top = topPadding)
            )

            // 수록곡 목록
            PlaylistBsideTrackContent(bSideTrack = playlist.bSideTrack)

            // 참여 아티스트 정보
            ArtistLayout(
                title = "아티스트",
                subscription = "플레이리스트를 빛낸 아티스트들이에요",
                artistList = artistMockData
            )
        }
    }
}