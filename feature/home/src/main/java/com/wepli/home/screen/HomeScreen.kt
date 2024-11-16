package com.wepli.home.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.AppBarIcon
import appbar.ScrollableAppBar
import appbar.WePLiAppBar
import com.wepli.designsystem.R
import com.wepli.home.component.PlayListCoverItem
import com.wepli.home.component.RelaylistBackground
import com.wepli.home.component.RelaylistBannerComponent
import com.wepli.home.component.WePLiBanner
import com.wepli.home.component.WePLiBannerType
import com.wepli.home.viewmodel.HomeViewModel
import com.wepli.shared.feature.mock.artistMockData
import com.wepli.shared.feature.mock.musicMockData
import com.wepli.shared.feature.mock.recommendPlaylistMockData
import com.wepli.shared.feature.mock.replaylistMockData
import com.wepli.uimodel.artist.ArtistUiData
import com.wepli.uimodel.music.ChartMusicUiData
import compose.MeasuredHeightContainer
import custom.ArtistProfileListItem
import custom.BlurBackgroundOverlay
import custom.MusicItem
import custom.MusicItemType
import custom.OneLineTitle
import custom.TwoLineTitle
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import extensions.calculateCurrentOffsetForPage
import extensions.gesturesDisabled
import extensions.pagerFadeTransition
import image.AsyncImageWithPreview
import model.playlist.RecommendPlaylist
import theme.WePLiTheme

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigatePlaylist: (playlist: RecommendPlaylist) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val topChartList by rememberUpdatedState(newValue = state.topChartList)
    val artistList by rememberUpdatedState(newValue = state.artistList)
    val recommendPlaylists by rememberUpdatedState(newValue = state.recommendPlaylists)
    val themePlaylists by rememberUpdatedState(newValue = state.themePlaylists)

    HomeScreen(
        topChartList = topChartList,
        artistList = artistList,
        recommendPlaylists = recommendPlaylists,
        themePlaylists = themePlaylists,
        onNavigatePlaylist = { playlist -> onNavigatePlaylist(playlist) }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    topChartList: List<ChartMusicUiData>,
    artistList: List<ArtistUiData>,
    recommendPlaylists: List<RecommendPlaylist>,
    themePlaylists: List<RecommendPlaylist>,
    onNavigatePlaylist: (playlist: RecommendPlaylist) -> Unit,
) {
    val scrollState = rememberLazyListState()
    val hazeState = remember { HazeState() }

    ScrollableAppBar(
        scrollState = scrollState,
        backgroundColors = Color.Transparent to Color.Black.copy(0.5f),
        contentsColors = Color.White to Color.White,
        topBarComponent = { backgroundColor, contentsColor, _, scrollFraction ->
            WePLiAppBar(
                modifier = Modifier
                    .hazeChild(
                        state = hazeState,
                        style = HazeStyle(
                            backgroundColor = backgroundColor,
                            blurRadius = (scrollFraction * 24).dp,
                            tint = HazeTint(color = backgroundColor),
                        ),
                    ),
                showLogo = true,
                showBackButton = false,
                containerColor = backgroundColor,
                contentsColor = contentsColor,
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
                        onClick = { },
                        badgeVisible = true
                    )
                }
            )
        }
    ) { paddingValues ->
        val (topPadding, bottomPadding) = paddingValues.calculateTopPadding() to paddingValues.calculateBottomPadding()

        LazyColumn(
            modifier = Modifier
                .haze(hazeState)
                .background(WePLiTheme.color.black)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(bottom = bottomPadding * 3),
            state = scrollState
        ) {
            item {
                RelaylistPagerLayout(topPagerModifier = Modifier.padding(top = topPadding))
            }

            item { WePLiChartLayout(musicList = topChartList) }

            item { WePLiBannerLayout() }

            item { ArtistLayout(artistList) }

            item {
                WePLiPlaylistLayout(title = "위플리 추천 플레이리스트", playlists = recommendPlaylists, onClick = { onNavigatePlaylist(it) })
            }

            item {
                WePLiPlaylistLayout(title = "테마별 플레이리스트", playlists = themePlaylists, onClick = { onNavigatePlaylist(it) })
            }
        }
    }
}


@SuppressLint("RestrictedApi")
@Composable
fun RelaylistPagerLayout(
    modifier: Modifier = Modifier,
    topPagerModifier: Modifier = Modifier,
) {
    val mockData = replaylistMockData.toList()
    val topPagerState = rememberPagerState(
        pageCount = { mockData.size }
    )
    val bottomPagerState = rememberPagerState(
        pageCount = { mockData.size }
    )

    val scaleSizeRatio = 0.8f

    // 상위 Pager 스크롤에 따라 하위 Pager를 동기화
    LaunchedEffect(topPagerState) {
        snapshotFlow { topPagerState.currentPageOffsetFraction }
            .collect { offset ->
                // 하위 Pager의 오프셋을 상위 Pager와 동일하게 업데이트
                bottomPagerState.scrollToPage(topPagerState.currentPage, offset)
            }
    }

    Box(modifier = modifier) {
        HorizontalPager(
            state = bottomPagerState, // 상단 Pager와 동기화
            modifier = Modifier
                .matchParentSize()
                .gesturesDisabled(disabled = true)
        ) { page ->
            val item = mockData[page]

            RelaylistBackground(
                modifier = Modifier.matchParentSize(),
                item = item,
                page = page,
                bottomPagerState = bottomPagerState
            )
        }

        HorizontalPager(
            state = topPagerState,
            modifier = topPagerModifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            contentPadding = PaddingValues(horizontal = 20.dp),
            pageSpacing = 12.dp
        ) { page ->
            val item = mockData[page]
            val pageOffset = topPagerState.calculateCurrentOffsetForPage(page)

            RelaylistBannerComponent(item = item, scaleSizeRatio = scaleSizeRatio, pageOffset = pageOffset)
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
fun WePLiChartLayout(
    modifier: Modifier = Modifier,
    musicList: List<ChartMusicUiData>
) {
    if (musicList.isEmpty()) return

    val pageCount = remember(key1 = musicList.size) { musicList.size / 5 }
    val pagerState = rememberPagerState(
        pageCount = { pageCount }
    )
    val musicChunk = remember(musicList) {
        musicList.chunked(5)
    }

    Column(modifier = modifier) {
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
    onClick: (playlist: RecommendPlaylist) -> Unit = {},
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
                        modifier = Modifier.clickable { onClick(playlist) },
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
fun HomeScreenPreview() {
    HomeScreen(
        topChartList = musicMockData,
        artistList = artistMockData,
        recommendPlaylists = recommendPlaylistMockData,
        themePlaylists = recommendPlaylistMockData,
        onNavigatePlaylist = {}
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