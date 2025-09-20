package com.wepli.home.screen.relaylist

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wepli.core.kotlin.time.formatAsRemainingTime
import com.wepli.core.resources.R as CoreR
import com.wepli.home.component.RelaylistBackground
import com.wepli.home.mvi.HomeUiState
import com.wepli.shared.feature.uimodel.relaylist.RelaylistUiData
import com.wepli.uimodel.music.SongUiData
import compose.noRippleClickable
import compose.pagerFadeTransition
import compose.pagerZoomOut
import image.AsyncImageWithPreview
import theme.LocalWindowWidthSizeClass
import theme.WepliTheme

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
                    .pagerFadeTransition(bottomPagerState, page), // 전환 효과 적용
                imageUrl = relaylist.coverImgUrl,
            )
        }

        HorizontalPager(
            state = topPagerState,
            modifier = topPagerModifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            pageSpacing = 12.dp,
        ) { page ->
            val relaylist = relaylists[page]

            RelaylistBanner(
                item = relaylist,
                remainingTime = state.currentRelaylistRemainingTime,
                modifier = Modifier
                    .noRippleClickable {
                        onClick.invoke(relaylist.id)
                    }
                    .padding(horizontal = 20.dp)
                    .pagerZoomOut(topPagerState, page),
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
            WindowWidthSizeClass.Compact -> 1f / 1.4f
            else -> 1f / 0.8f
        }
    }

    Column(
        modifier = modifier.aspectRatio(ratio),
        verticalArrangement = Arrangement.Bottom
    ) {
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
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = firstSong.title,
                style = WepliTheme.typo.subTitle1,
                color = WepliTheme.color.white,
                maxLines = 1,
            )

            Text(
                text = firstSong.artistName,
                style = WepliTheme.typo.body4,
                color = WepliTheme.color.gray700,
                maxLines = 1,
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
            .padding(horizontal = 20.dp, vertical = 16.dp),
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

@Preview
@Composable
private fun RelaylistTimerPreview() {
    RelaylistTimerComponent(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        remainingTime = 3661000L
    )
}