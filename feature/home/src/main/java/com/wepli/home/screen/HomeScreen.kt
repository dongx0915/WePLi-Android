package com.wepli.home.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.HomeAppBar
import appbar.WepliAppBar
import com.wepli.core.kotlin.time.formatAsRemainingTime
import com.wepli.feature.home.R
import com.wepli.home.component.PlayListCoverItem
import com.wepli.home.component.RelaylistBackground
import com.wepli.home.component.WePLiBanner
import com.wepli.home.component.WePLiBannerType
import com.wepli.home.mvi.HomeEffect
import com.wepli.home.mvi.HomeIntent
import com.wepli.home.mvi.HomeUiState
import com.wepli.home.viewmodel.HomeViewModel
import com.wepli.shared.feature.mock.artistMockData
import com.wepli.shared.feature.mock.musicMockData
import com.wepli.shared.feature.mock.recommendPlaylistMockData
import com.wepli.shared.feature.mock.relaylistUiMockData
import com.wepli.shared.feature.uimodel.artist.ArtistUiData
import com.wepli.shared.feature.uimodel.relaylist.RelaylistUiData
import com.wepli.uimodel.music.ChartMusicUiData
import com.wepli.uimodel.music.SongUiData
import compose.MeasuredHeightContainer
import compose.calculateCurrentOffsetForPage
import compose.pagerFadeTransition
import custom.ArtistProfileListItem
import custom.MusicItem
import custom.MusicItemType
import custom.OneLineTitle
import custom.TwoLineTitle
import dev.chrisbanes.haze.hazeSource
import image.AsyncImageWithPreview
import model.playlist.RecommendPlaylist
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import theme.LocalHazeState
import theme.LocalWindowWidthSizeClass
import theme.WepliTheme
import kotlin.math.absoluteValue
import com.wepli.core.resources.R as CoreR

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


@SuppressLint("RestrictedApi")
@Composable
fun RelaylistPagerLayout(
    modifier: Modifier = Modifier,
    topPagerModifier: Modifier = Modifier,
    state: HomeUiState,
    onClick: (relaylistId: Int) -> Unit,
    onPageChanged: (Int) -> Unit,
) {
    val relaylists = state.relaylists
    val topPagerState = rememberPagerState(
        pageCount = { relaylists.size }
    )
    val bottomPagerState = rememberPagerState(
        pageCount = { relaylists.size }
    )

    // 상위 Pager 스크롤에 따라 하위 Pager를 동기화
    LaunchedEffect(topPagerState) {
        snapshotFlow { topPagerState.currentPage }
            .collect { page ->
                onPageChanged(page)
            }
    }

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
            userScrollEnabled = false,
            modifier = Modifier.matchParentSize()
        ) { page ->
            val relaylist = relaylists[page]

            RelaylistBackground(
                modifier = Modifier
                    .matchParentSize()
                    .pagerFadeTransition(page, bottomPagerState), // 전환 효과 적용
                imageUrl = relaylist.coverImgUrl,
            )
        }

        HorizontalPager(
            state = topPagerState,
            modifier = topPagerModifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            pageSpacing = 12.dp
        ) { page ->
            val relaylist = relaylists[page]
            val pageOffset = topPagerState.calculateCurrentOffsetForPage(page)
            val scaledFraction: (scale: Int) -> Float = { (pageOffset.absoluteValue * it).coerceIn(0f, 1f) }

            RelaylistBanner(
                item = relaylist,
                remainingTime = state.currentRelaylistRemainingTime,
                modifier = Modifier
                    .clickable { onClick.invoke(relaylist.id) }
                    .padding(horizontal = 20.dp)
                    .graphicsLayer {
                        alpha = 1 - scaledFraction(2)
                        translationY = lerp(start = 0f, stop = 100f, fraction = scaledFraction(1))
                    }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
private fun RelaylistBanner(
    modifier: Modifier = Modifier,
    item: RelaylistUiData,
    remainingTime: Long,
) {
    val firstSong: SongUiData? = item.bSideTrack.firstOrNull()
    val windowWidthSizeClass = LocalWindowWidthSizeClass.current
    val ratio = remember(windowWidthSizeClass) {
        when (windowWidthSizeClass) {
            WindowWidthSizeClass.Compact -> 10f / 5f
            else -> 10f / 4f
        }
    }

    Column(modifier) {
        Spacer(modifier = Modifier.aspectRatio(ratio))
        Text(
            text = item.title,
            style = WepliTheme.typo.title1,
            color = WepliTheme.color.white,
        )
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "총 ${item.songCount}곡 • ${item.voteCount} 투표",
            style = WepliTheme.typo.body3,
            color = WepliTheme.color.gray700
        )
        Spacer(modifier = Modifier.height(32.dp))

        if (firstSong != null) {
            RelaylistFirstSong(firstSong = firstSong)
        }

        Spacer(modifier = Modifier.height(36.dp))
        RelaylistTimerComponent(remainingTime = remainingTime)
    }
}

@Composable
private fun RelaylistFirstSong(modifier: Modifier = Modifier, firstSong: SongUiData) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = firstSong.title,
                style = WepliTheme.typo.subTitle1,
                color = WepliTheme.color.white
            )

            Text(
                text = firstSong.artistName,
                style = WepliTheme.typo.body4,
                color = WepliTheme.color.gray700
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(4.dp))

        AsyncImageWithPreview(
            imageUrl = firstSong.getImageUrl(),
            previewImage = painterResource(id = CoreR.drawable.img_placeholder_chuu),
            imageOverrideSize = 60.dp,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(6.dp))
        )
    }
}

@Composable
private fun RelaylistTimerComponent(modifier: Modifier = Modifier, remainingTime: Long) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = WepliTheme.color.white.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 20.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (remainingTime <= 0L) {
            Text(
                text = "릴레이리스트가 완성되었어요 🎉",
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                style = WepliTheme.typo.subTitle2,
                color = WepliTheme.color.gray900,
                modifier = Modifier.fillMaxWidth()
            )
            return@Row
        } else {
            Text(
                text = "플리 완성까지",
                style = WepliTheme.typo.subTitle2,
                color = WepliTheme.color.gray900,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = remainingTime.formatAsRemainingTime(),
                style = WepliTheme.typo.body4,
                color = WepliTheme.color.gray700,
            )
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

    val pageCount = remember(musicList.size) { musicList.size / 5 }
    val pagerState = rememberPagerState(
        pageCount = { pageCount }
    )
    val musicChunk = remember(musicList.size) {
        musicList.chunked(5)
    }

    Column(modifier = modifier) {
        TwoLineTitle(
            title = stringResource(R.string.home_top_100_title),
            subscription = stringResource(R.string.home_top_100_update_time)
        )
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
    onClick: (playlistId: Int) -> Unit = {},
) {
    val playlistWithMaxTitle = remember(playlists.size) {
        playlists.maxByOrNull { it.title.length }
    } ?: return

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
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
                        modifier = Modifier.clickable { onClick(playlist.id) },
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
            title = stringResource(R.string.home_top_artist_title),
            subscription = stringResource(R.string.home_top_artist_desc),
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