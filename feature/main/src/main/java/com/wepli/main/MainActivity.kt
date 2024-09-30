package com.wepli.main

import ArtistProfileListItem
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import appbar.WePLiAppBar
import com.wepli.component.MusicItem
import com.wepli.component.WePLiBanner
import com.wepli.component.WePLiBannerType
import dagger.hilt.android.AndroidEntryPoint
import extensions.setStatusBarColor
import model.Artist
import model.MusicData
import theme.WePLiTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // enableEdgeToEdge()
        setStatusBarColor(Color.Black, darkIcons = false)
        setContent {
            WePLiTheme {
                MainScreen(viewModel)
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    Scaffold(
        containerColor = WePLiTheme.color.black,
        topBar = {
            WePLiAppBar(
                showLogo = true,
                showBackButton = false,
                showSearchButton = true,
                showNotificationButton = true,
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { WePLiBannerLayout() }

            item { WePLiChartLayout(viewModel.musicList.value) }

            item { ArtistLayout(viewModel.artistList.value) }
        }
    }
}

@Composable
fun TitleItem(
    title: String?,
    subscription: String?,
) {
    Column(
        modifier = Modifier.padding(vertical = 12.dp, horizontal = 20.dp)
    ) {
        title?.let {
            Text(
                text = it,
                style = WePLiTheme.typo.subTitle2,
                color = WePLiTheme.color.gray900,
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        subscription?.let {
            Text(
                text = it,
                style = WePLiTheme.typo.body6,
                color = WePLiTheme.color.gray600,
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
        pageSpacing = 16.dp
    ) { page ->
        val banner = bannerList[page]
        WePLiBanner(modifier = Modifier.fillMaxWidth(), bannerType = banner)
    }
}

@Composable
fun WePLiChartLayout(musicList: List<MusicData>) {
    val pageCount = remember { musicList.size / 5 }
    val pagerState = rememberPagerState(
        pageCount = { pageCount }
    )

    Column {
        TitleItem(title = "위플리 TOP 100", subscription = "6월 23일 오전 7시 업데이트")
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            state = pagerState,
            contentPadding = PaddingValues(start = 20.dp, end = 10.dp),
        ) { page ->
            val musicChunk = musicList.chunked(5)[page]

            // LazyColumn 내에 동일한 스크롤 방향의 LazyColumn 추가 불가
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                musicChunk.forEach { music ->
                    MusicItem(musicData = music, modifier = Modifier.padding(end = 12.dp))
                }
            }
        }
    }
}

@Composable
fun ArtistLayout(artistList: List<Artist>) {
    Column {
        TitleItem(
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
    MainScreen(viewModel = MainViewModel())
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
    val musicList = remember { MainViewModel().musicList.value }

    WePLiChartLayout(musicList = musicList)
}

@Preview
@Composable
fun ArtistLayoutPreview() {
    val artists = remember { MainViewModel().artistList.value }
    ArtistLayout(
        artistList = artists
    )
}