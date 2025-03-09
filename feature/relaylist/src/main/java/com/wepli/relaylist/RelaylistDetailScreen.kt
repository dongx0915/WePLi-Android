package com.wepli.relaylist

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import appbar.RelaylistAppBar
import com.wepli.designsystem.R
import com.wepli.shared.feature.mock.relaylistUiMockData
import com.wepli.shared.feature.mock.songMockData
import com.wepli.uimodel.music.SongUiData
import custom.MusicItem
import custom.MusicItemType
import extensions.compose.toPx
import image.AsyncImageWithPreview
import theme.WepliTheme

@Composable
fun RelaylistDetailScreenRoute(
    navOnBack: () -> Unit
) {
    RelaylistDetailScreen()
}

@Preview
@Composable
fun RelaylistDetailScreen() {
    val relaylist = relaylistUiMockData.random()

    RelaylistAppBar(
        onClickBack = {}
    ) { scrollState, paddingValue ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // 백그라운드
            AsyncImageWithPreview(
                imageUrl = relaylist.coverImgUrl,
                contentScale = ContentScale.Crop,
                previewImage = painterResource(id = R.drawable.img_placeholder_chuu_3),
                modifier = Modifier
                    .fillMaxSize()
                    .blur(20.dp),
            )

            // 릴레이리스트 콘텐츠
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(WepliTheme.color.black.copy(alpha = 0.8f))
                    .verticalScroll(scrollState)
                    .padding(paddingValue)
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
                    text = "첫 사랑의 달콤하고 아련한 추억을 되살리는 노래들로 가득한 플레이리스트입니다." +
                            "\n\n" +
                            "이 멜로디와 함께 잊혀진 감정의 페이지를 넘겨보세요.",
                    textAlign = TextAlign.Center,
                    style = WepliTheme.typo.body4,
                    color = WepliTheme.color.gray700,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                )

                RelaylistTimerComponent(relaylist.formatMilliseconds())

                SongRankingLayout(
                    modifier = Modifier.padding(top = 36.dp)
                )
            }
        }
    }
}

@Composable
private fun RelaylistTimerComponent(remainingTime: String) {
    Row(
        modifier = Modifier
            .padding(top = 32.dp)
            .background(
                color = WepliTheme.color.white.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 16.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "완성까지 남은 시간",
            style = WepliTheme.typo.subTitle2,
            color = WepliTheme.color.gray900,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = remainingTime,
            style = WepliTheme.typo.body4,
            color = WepliTheme.color.gray700,
        )
    }
}

@Preview
@Composable
private fun SongRankingLayout(modifier: Modifier = Modifier) {
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

        RelaylistBsideTrackContent(
            modifier = Modifier.padding(top = 24.dp),
            bSideTrack = songMockData.take(2)
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
        bSideTrack.forEach { song ->
            MusicItem(
                imageModifier = Modifier
                    .size(imageSize)
                    .clip(RoundedCornerShape(3.dp)),
                musicItemType = MusicItemType.Normal(song, imageSize.toPx()),
                showMoreIcon = true
            )
        }
    }
}