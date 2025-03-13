package com.wepli.relaylist.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.RelaylistAppBar
import button.WepliBasicButton
import button.WepliButtonStyle
import com.wepli.core.kotlin.time.formatAsRemainingTime
import com.wepli.designsystem.R
import com.wepli.relaylist.detail.mvi.RelaylistDetailIntent
import com.wepli.relaylist.detail.mvi.RelaylistDetailUiState
import com.wepli.shared.feature.mock.relaylistUiMockData
import com.wepli.uimodel.music.SongUiData
import custom.MusicItem
import custom.MusicItemType
import extensions.compose.toPx
import image.AsyncImageWithPreview
import org.orbitmvi.orbit.compose.collectAsState
import theme.WepliTheme

@Composable
fun RelaylistDetailScreenRoute(
    relaylistId: Int,
    navOnBack: () -> Unit
) {
    val viewModel: RelaylistDetailViewModel = hiltViewModel()
    val state by viewModel.collectAsState()

    LaunchedEffect(relaylistId) {
        viewModel.processIntent(RelaylistDetailIntent.LoadRelaylist(relaylistId))
    }

    RelaylistDetailScreen(
        state = state,
        navOnBack = { navOnBack() }
    )
}

@Composable
fun RelaylistDetailScreen(
    state: RelaylistDetailUiState,
    navOnBack: () -> Unit,
) {
    val relaylist = state.relaylist

    RelaylistAppBar(
        relaylistTitle = relaylist.title,
        onClickBack = navOnBack,
    ) { scrollState, paddingValue ->
        Box(modifier = Modifier.fillMaxSize()) {
            // 백그라운드
            AsyncImageWithPreview(
                imageUrl = relaylist.coverImgUrl,
                imageOverrideSize = 1.dp, // 성능을 위해 다운샘플링
                contentScale = ContentScale.Crop,
                previewImage = painterResource(id = R.drawable.img_placeholder_chuu_3),
                modifier = Modifier
                    .fillMaxSize()
                    .blur(48.dp),
            )

            // 릴레이리스트 콘텐츠
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(WepliTheme.color.black.copy(alpha = 0.6f))
                    .padding(paddingValue)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                // 릴레이리스트 커버 이미지
                AsyncImageWithPreview(
                    imageUrl = relaylist.coverImgUrl,
                    contentScale = ContentScale.Crop,
                    previewImage = painterResource(id = R.drawable.img_placeholder_chuu_3),
                    modifier = Modifier
                        .widthIn(max = 180.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                )

                Text(
                    text = relaylist.title,
                    style = WepliTheme.typo.subTitle1,
                    color = WepliTheme.color.gray900,
                    modifier = Modifier.padding(top = 24.dp)
                )

                Text(
                    text = "${relaylist.formattedCreatedAt} • 총 ${relaylist.songCnt}곡",
                    style = WepliTheme.typo.body3,
                    color = WepliTheme.color.gray700,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = relaylist.description,
                    textAlign = TextAlign.Center,
                    style = WepliTheme.typo.body4,
                    color = WepliTheme.color.gray700,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                )

                RelaylistTimerComponent(state.remainingTime)

                SongRankingLayout(
                    bSideTrack = state.relaylist.bSideTrack,
                    modifier = Modifier.padding(top = 36.dp)
                )

                Spacer(modifier = Modifier.weight(1f))
                WepliBasicButton(
                    title = "노래 투표하기",
                    isEnabled = true,
                    onClick = {  },
                    modifier = Modifier
                        .padding(top = 60.dp, bottom = 20.dp)
                        .align(Alignment.CenterHorizontally),
                    buttonStyle = WepliButtonStyle.Transparent(alpha = 0.1f),
                )
            }
        }
    }
}

@Composable
private fun RelaylistTimerComponent(remainingTime: Long) {
    Row(
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxWidth()
            .background(
                color = WepliTheme.color.white.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 16.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (remainingTime <= 0L) {
            Text(
                text = "릴레이리스트가 완성되었어요 🎉",
                textAlign = TextAlign.Center,
                style = WepliTheme.typo.subTitle2,
                color = WepliTheme.color.gray900,
                modifier = Modifier.fillMaxWidth()
            )
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
private fun SongRankingLayout(
    bSideTrack: List<SongUiData>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "현재 곡 순위",
                style = WepliTheme.typo.subTitle1,
                color = WepliTheme.color.gray900,
            )

            Text(
                text = "총 n명 참여",
                style = WepliTheme.typo.body6,
                color = WepliTheme.color.gray700,
            )
        }

        if (bSideTrack.isEmpty()) {
            EmptySongLayout()
        } else {
            RelaylistBsideTrackContent(
                modifier = Modifier.padding(top = 24.dp),
                bSideTrack = bSideTrack
            )
        }
    }
}

@Composable
private fun EmptySongLayout(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 100.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "아직 등록된 곡이 없어요.",
            style = WepliTheme.typo.subTitle2,
            color = WepliTheme.color.gray900,
        )

        Text(
            text = "주제에 맞는 명곡을 다른 사람들에게 추천해주세요!",
            style = WepliTheme.typo.body5,
            color = WepliTheme.color.gray600,
        )

        Text(
            text = "노래 추천하기",
            style = WepliTheme.typo.subTitle5,
            color = WepliTheme.color.gray600,
            modifier = Modifier
                .clickable { }
                .padding(top = 20.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(WepliTheme.color.white.copy(alpha = 0.1f))
                .padding(vertical = 8.dp, horizontal = 12.dp)
        )
    }
}

@Composable
private fun RelaylistBsideTrackContent(
    modifier: Modifier = Modifier,
    bSideTrack: List<SongUiData>
) {
    val imageSize = 52.dp

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        bSideTrack.forEachIndexed { index, song ->
            MusicItem(
                imageModifier = Modifier
                    .size(imageSize)
                    .clip(RoundedCornerShape(3.dp)),
                musicItemType = MusicItemType.Normal(song, imageSize.toPx(), index + 1),
                showMoreIcon = true
            )
        }
    }
}

@Preview
@Composable
private fun RelaylistDetailScreenPreview() {
    RelaylistDetailScreen(
        state = RelaylistDetailUiState(
            relaylist = relaylistUiMockData.random(),
            remainingTime = 1600000L
        ),
        navOnBack = { }
    )
}