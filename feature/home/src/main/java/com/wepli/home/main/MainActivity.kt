package com.wepli.home.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.AppBarIcon
import appbar.WePLiAppBar
import com.wepli.designsystem.R
import custom.ArtistProfileListItem
import custom.OneLineTitle
import com.wepli.home.component.PlayListCoverItem
import custom.TwoLineTitle
import com.wepli.home.component.WePLiBanner
import com.wepli.home.component.WePLiBannerType
import com.wepli.navigator.feature.community.CommunityNavigator
import com.wepli.home.state.HomeUiState
import com.wepli.navigator.feature.playlist.PlaylistNavigator
import com.wepli.shared.feature.mock.artistMockData
import com.wepli.shared.feature.mock.musicMockData
import com.wepli.shared.feature.mock.recommendPlaylistMockData
import com.wepli.uimodel.artist.ArtistUiData
import com.wepli.uimodel.music.ChartMusicUiData
import compose.MeasuredHeightContainer
import custom.MusicItem
import custom.MusicItemType
import dagger.hilt.android.AndroidEntryPoint
import extensions.setStatusBarColor
import model.playlist.RecommendPlaylist
import theme.WePLiTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var communityNavigator: CommunityNavigator
    @Inject lateinit var playlistNavigator: PlaylistNavigator

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // enableEdgeToEdge()
        setStatusBarColor(Color.Black, darkIcons = false)
        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()

            WePLiTheme {
                MainScreen(
                    state = state,
                    onNavigateCommunity = {
                        communityNavigator.navigateFrom(this)
                    },
                    onNavigatePlaylist = {
                        playlistNavigator.navigateFrom(this)
                    }
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: HomeUiState,
    onNavigateCommunity: () -> Unit = {},
    onNavigatePlaylist: () -> Unit = {},
) {
    Scaffold(
        containerColor = WePLiTheme.color.black,
        topBar = {
            WePLiAppBar(
                showLogo = true,
                showBackButton = false,
                actionIcons = listOf {
                    AppBarIcon(
                        modifier = Modifier.offset(x = (-6).dp),
                        iconResource = R.drawable.ic_search,
                        iconColor = WePLiTheme.color.white,
                        onClick = { }
                    )
                    AppBarIcon(
                        modifier = Modifier.offset(x = (-6).dp),
                        iconResource = R.drawable.ic_alarm,
                        iconColor = WePLiTheme.color.white,
                        onClick = { onNavigateCommunity.invoke()},
                        badgeVisible = true
                    )
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(bottom = 40.dp)
        ) {
            item {
                val topChartList by rememberUpdatedState(newValue = state.topChartList)
                WePLiChartLayout(topChartList)
            }

            item { WePLiBannerLayout() }

            item {
                val artistList by rememberUpdatedState(newValue = state.artistList)
                ArtistLayout(artistList)
            }

            item {
                val recommendPlaylists by rememberUpdatedState(newValue = state.recommendPlaylists)
                WePLiPlaylistLayout(title = "위플리 추천 플레이리스트", playlists = recommendPlaylists, onClick = { onNavigatePlaylist() })
            }

            item {
                val themePlaylists by rememberUpdatedState(newValue = state.themePlaylists)
                WePLiPlaylistLayout(title = "테마별 플레이리스트", playlists = themePlaylists, onClick = { onNavigatePlaylist() })
            }
        }
    }
}

@Composable
fun WePLiBannerLayout() {
    val bannerList = listOf(
        WePLiBannerType.Twitter,
        WePLiBannerType.Instagram
    )
    val pagerState = rememberPagerState(pageCount = { bannerList.size })

    HorizontalPager(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 20.dp),
        pageSpacing = 12.dp
    ) { page ->
        val banner = bannerList[page]
        WePLiBanner(modifier = Modifier.fillMaxWidth(), bannerType = banner)
    }
}

@Composable
fun WePLiChartLayout(musicList: List<ChartMusicUiData>) {
    if (musicList.isEmpty()) return

    val pageCount = remember(key1 = musicList.size) { musicList.size / 5 }
    val pagerState = rememberPagerState(
        pageCount = { pageCount }
    )
    val musicChunk = remember(musicList) {
        musicList.chunked(5)
    }

    Column {
        TwoLineTitle(title = "위플리 TOP 100", subscription = "6월 23일 오전 7시 업데이트")
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            state = pagerState,
            contentPadding = PaddingValues(start = 20.dp, end = 10.dp),
        ) { page ->
            // LazyColumn 내에 동일한 스크롤 방향의 LazyColumn 추가 불가
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                musicChunk[page].forEach { music ->
                    MusicItem(
                        modifier = Modifier.padding(end = 22.dp),
                        musicItemType = MusicItemType.Chart(music),
                        showPlayIcon = true,
                    )
                }
            }
        }
    }
}

@Composable
fun WePLiPlaylistLayout(
    title: String,
    playlists: List<RecommendPlaylist>,
    onClick: () -> Unit = {},
) {
    val playlistWithMaxTitle = remember(key1 = playlists) {
        playlists.maxByOrNull { it.title.length }
    } ?: return

    Column {
        OneLineTitle(title = title)

        MeasuredHeightContainer(
            modifier = Modifier,
            measured = {
                PlayListCoverItem(recommendPlaylist = playlistWithMaxTitle)
            },
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                items(playlists) { playlist ->
                    PlayListCoverItem(
                        modifier = Modifier.clickable { onClick() },
                        recommendPlaylist = playlist,
                    )
                }
            }
        }
    }
}

@Composable
fun ArtistLayout(artistList: List<ArtistUiData>) {
    if (artistList.isEmpty()) return

    Column {
        TwoLineTitle(
            title = "위플리 인기 랭킹",
            subscription = "위플리 차트에서 인기가 많은 가수들을 모아봤어요"
        )
        Spacer(modifier = Modifier.height(4.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            items(artistList) { artist ->
                ArtistProfileListItem(artist)
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(
        HomeUiState(
            topChartList = musicMockData,
            artistList = artistMockData,
            recommendPlaylists = recommendPlaylistMockData
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun AppBarPreview() {
    WePLiAppBar(
        title = "타이틀",
    )
}

@Preview
@Composable
fun WePLiChartPreview() {
    WePLiChartLayout(musicList = musicMockData)
}

@Preview
@Composable
fun ArtistLayoutPreview() {
    ArtistLayout(artistList = artistMockData)
}