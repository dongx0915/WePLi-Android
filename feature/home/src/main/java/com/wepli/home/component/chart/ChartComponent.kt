package com.wepli.home.component.chart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wepli.feature.home.R
import com.wepli.shared.feature.mock.musicMockData
import com.wepli.uimodel.music.ChartMusicUiData
import custom.MusicItem
import custom.MusicItemType
import custom.TwoLineTitle
import kotlin.collections.forEach

@Preview
@Composable
fun WePLiChartPreview() {
    WePLiChartLayout(musicList = musicMockData)
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