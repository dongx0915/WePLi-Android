package com.wepli.home.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.HomeAppBar
import appbar.WepliAppBar
import com.wepli.feature.home.R
import com.wepli.home.component.PlayListCoverItem
import com.wepli.home.component.WePLiBanner
import com.wepli.home.component.WePLiBannerType
import com.wepli.home.mvi.HomeEffect
import com.wepli.home.mvi.HomeIntent
import com.wepli.home.mvi.HomeUiState
import com.wepli.home.screen.relaylist.ArtistLayout
import com.wepli.home.screen.relaylist.RelaylistPagerLayout
import com.wepli.home.screen.relaylist.WePLiBannerLayout
import com.wepli.home.screen.relaylist.WePLiChartLayout
import com.wepli.home.screen.relaylist.WePLiPlaylistLayout
import com.wepli.home.viewmodel.HomeViewModel
import com.wepli.shared.feature.mock.artistMockData
import com.wepli.shared.feature.mock.musicMockData
import com.wepli.shared.feature.mock.recommendPlaylistMockData
import com.wepli.shared.feature.mock.relaylistUiMockData
import com.wepli.shared.feature.uimodel.artist.ArtistUiData
import com.wepli.uimodel.music.ChartMusicUiData
import compose.MeasuredHeightContainer
import custom.ArtistProfileListItem
import custom.MusicItem
import custom.MusicItemType
import custom.OneLineTitle
import custom.TwoLineTitle
import dev.chrisbanes.haze.hazeSource
import model.playlist.RecommendPlaylist
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import theme.LocalHazeState
import theme.WepliTheme

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigatePlaylist: (playlistId: Int) -> Unit,
    onNavigateRelaylist: (relaylistId: Int) -> Unit,
) {
    val state by viewModel.collectAsState()
    val context = LocalContext.current

    viewModel.collectSideEffect {
        when (it) {
            is HomeEffect.PlaylistLoadSuccess -> {
                onNavigatePlaylist(it.playlistId)
            }
            is HomeEffect.RelaylistLoadSuccess -> {
                onNavigateRelaylist(it.relaylistId)
            }

            HomeEffect.RelaylistLoadFailed -> {
                Toast.makeText(context, "릴레이리스트 조회에 실패했어요.", Toast.LENGTH_SHORT).show()
            }
            HomeEffect.PlaylistLoadFailed -> {
                Toast.makeText(context, "플레이리스트 조회에 실패했어요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    HomeScreen(
        state = state,
        sendAction = viewModel::processIntent,
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun HomeScreen(
    state: HomeUiState,
    sendAction: (HomeIntent) -> Unit,
) {
    val hazeState = LocalHazeState.current
    val topChartList = state.topChartList
    val artistList = state.artistList
    val recommendPlaylists = state.recommendPlaylists
    val themePlaylists = state.themePlaylists

    HomeAppBar { scrollState, paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()
        val bottomPadding = paddingValues.calculateBottomPadding()

        LazyColumn(
            modifier = Modifier
                .hazeSource(hazeState)
                .background(WepliTheme.color.black)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(bottom = 50.dp + bottomPadding * 2),
            state = scrollState
        ) {
            item {
                RelaylistPagerLayout(
                    topPagerModifier = Modifier.padding(top = topPadding),
                    state = state,
                    onClick = { relaylistId -> sendAction(HomeIntent.LoadRelaylist(relaylistId)) },
                    onPageChanged = { page -> sendAction(HomeIntent.UpdateCurrentPage(page))}
                )
            }

            item { WePLiChartLayout(musicList = topChartList) }

            item { WePLiBannerLayout() }

            item { ArtistLayout(artistList) }

            item {
                WePLiPlaylistLayout(
                    title = stringResource(R.string.home_recommend_playlist_title),
                    playlists = recommendPlaylists,
                    onClick = { playlistId -> sendAction(HomeIntent.LoadPlaylist(playlistId)) }
                )
            }

            item {
                WePLiPlaylistLayout(
                    title = stringResource(R.string.home_theme_playlist_title),
                    playlists = themePlaylists,
                    onClick = { playlistId -> sendAction(HomeIntent.LoadPlaylist(playlistId)) }
                )
            }
        }
    }
}

@Preview(heightDp = 2000)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        state = HomeUiState(
            relaylists = relaylistUiMockData,
            topChartList = musicMockData,
            artistList = artistMockData,
            recommendPlaylists = recommendPlaylistMockData,
            themePlaylists = recommendPlaylistMockData,
        ),
        sendAction = {},
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun AppBarPreview() {
    WepliAppBar(
        title = "타이틀",
    )
}

