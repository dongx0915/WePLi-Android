package com.wepli.playlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.ScrollableAppBar
import appbar.WePLiAppBar
import com.wepli.playlist.component.ArtistLayout
import com.wepli.playlist.component.PlaylistBsideTrackContent
import com.wepli.playlist.component.PlaylistHeader
import com.wepli.playlist.state.PlaylistState
import com.wepli.shared.feature.mock.artistMockData
import com.wepli.shared.feature.uimodel.playlist.PlaylistUiData
import dagger.hilt.android.AndroidEntryPoint
import theme.WePLiTheme
import java.util.Date

@AndroidEntryPoint
class PlaylistActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val viewModel: PlaylistViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()

            WePLiTheme {
                PlaylistScreen(state)
            }
        }
    }
}


@Preview
@Composable
fun PlaylistScreenPreview() {
    PlaylistScreen(
        state = PlaylistState(
            playlist = PlaylistUiData(
                id = 0,
                title = "[Playlist] 졸릴 때 잠깨기 좋은 청량맛 노동요\uD83E\uDDCA\uD83C\uDF0A\uD83D\uDC99",
                description = "나와 비슷한 취향을 가진 사람들이 올 한해 즐겨들었던 곡들을 모아봤어요. 다른 사람들은 어떤 노래를 듣는지 궁금할 때가 있잖아요. 특...나와 비슷한 취향을 가진 사람들이 올 한해 즐겨들었던 곡들을 모아봤어요. 다른 사람들은 어떤 노래를 듣는지 궁금할 때가 있잖아요. 특...나와 비슷한 취향을 가진 사람들이 올 한해 즐겨들었던 곡들을 모아봤어요. 다른 사람들은 어떤 노래를 듣는지 궁금할 때가 있잖아요. 특...나와 비슷한 취향을 가진 사람들이 올 한해 즐겨들었던 곡들을 모아봤어요. 다른 사람들은 어떤 노래를 듣는지 궁금할 때가 있잖아요. 특...",
                coverImgUrl = "",
                author = "WePLi",
                bSideTrack = emptyList(),
                artists = emptyList(),
                createdAt = Date()
            )
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistScreen(state: PlaylistState) {
    val scrollState = rememberScrollState()
    val playlist = remember { state.playlist }

    ScrollableAppBar(
        scrollState = scrollState,
        backgroundColors = Color.Transparent to Color.Black,
        contentsColors = Color.White to Color.White,
        topBarComponent = { backgroundColor, contentsColor ->
            WePLiAppBar(
                title = playlist.title,
                containerColor = backgroundColor,
                contentsColor = contentsColor,
                showBackButton = true,
                showLikeButton = true,
                showMoreButton = true,
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