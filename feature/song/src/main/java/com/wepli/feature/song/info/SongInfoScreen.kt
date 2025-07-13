package com.wepli.feature.song.info

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.ScrollableAppBar
import appbar.WepliAppBar
import component.youtube.YoutubeVideoPlayer
import com.wepli.core.resources.R as CoreR
import com.wepli.feature.song.info.component.album.AlbumInfoLayout
import com.wepli.feature.song.info.component.album.ResponsiveAlbumGrid
import com.wepli.feature.song.info.component.song.SimilarSongsLayout
import com.wepli.feature.song.info.component.song.SongDetailInfoLayout
import com.wepli.feature.song.info.mvi.SongInfoIntent
import com.wepli.feature.song.info.mvi.SongInfoUiState
import com.wepli.shared.feature.mock.songMockData
import com.wepli.shared.feature.uimodel.album.AlbumUiData
import com.wepli.shared.feature.uimodel.musicvideo.MusicVideoUiData
import com.wepli.uimodel.music.SongUiData
import common.WepliSpacer
import custom.OneLineTitle
import image.AsyncImageWithPreview
import org.orbitmvi.orbit.compose.collectAsState
import theme.WepliTheme

@Composable
fun SongInfoScreenRoute(
    song: SongUiData,
    navOnBack: () -> Unit
) {
    val viewModel: SongInfoViewModel = hiltViewModel()
    val state: SongInfoUiState by viewModel.collectAsState()

    LaunchedEffect(song) {
        viewModel.processIntent(SongInfoIntent.Init(song))
    }

    SongInfoScreen(
        state = state,
        sendAction = { viewModel.processIntent(it) },
        navOnBack = navOnBack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongInfoScreen(
    state: SongInfoUiState,
    sendAction: (SongInfoIntent) -> Unit,
    navOnBack: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val song = state.song

    ScrollableAppBar(
        scrollState = scrollState,
        backgroundColors = Color.Black to Color.Black,
        contentsColors = Color.White to Color.White,
        topBarComponent = { backgroundColor, contentsColor, _, scrollFraction ->
            WepliAppBar(
                title = "",
                containerColor = backgroundColor,
                contentsColor = contentsColor,
                showBackButton = true,
                onClickBack = navOnBack,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WepliTheme.color.black)
                .verticalScroll(scrollState)
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(64.dp)
        ) {
            SongInfoLayout(song = song)

            state.musicVideoState?.let { musicVideo ->
                MusicVideoInfoLayout(
                    musicVideo = musicVideo,
                    isExpanded = state.isMusicVideoExpanded,
                    onToggleExpanded = { sendAction(SongInfoIntent.ToggleMusicVideo) },
                )
            }

            SongDetailInfoLayout(
                composers = song.composers,
                genres = song.genres
            )

            AlbumInfoLayout(album = state.album)

            SimilarSongsLayout(similarSongs = state.similarSongs)

            ArtistAlbumGrid(albums = state.artistAlbums)
        }
    }
}

@Composable
fun MusicVideoInfoLayout(
    musicVideo: MusicVideoUiData,
    isExpanded: Boolean,
    onToggleExpanded: () -> Unit,
) {
    val videoSizeIcon by remember(isExpanded) {
        derivedStateOf {
            if (isExpanded) CoreR.drawable.ic_arrow_minimize else CoreR.drawable.ic_arrow_expand
        }
    }

    Column(
        modifier = Modifier.animateContentSize()
    ) {
        OneLineTitle(
            title = "뮤직비디오",
            showIcon = true,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        YoutubeVideoPlayer(
            videoId = musicVideo.id,
            forcePause = !isExpanded,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .wrapContentHeight()
                .animateContentSize()
                .let {
                    if (isExpanded) it
                    else it.height(0.dp)
                }
        )

        Row(
            modifier = Modifier.padding(top = 8.dp),
        ) {
            AsyncImageWithPreview(
                imageUrl = musicVideo.thumbnail,
                previewImage = painterResource(id = CoreR.drawable.img_placeholder_minnie),
                imageOverrideSize = 52.dp,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .animateContentSize()
                    .let {
                        if (isExpanded) it.size(0.dp)
                        else it.size(52.dp)
                    }
            )

            if (isExpanded.not()) {
                WepliSpacer(horizontal = 12.dp)
            }

            Row(
                modifier = Modifier.padding(top = if (isExpanded) 8.dp else 0.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = musicVideo.title,
                        style = WepliTheme.typo.body6,
                        color = WepliTheme.color.gray900,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = musicVideo.channelTitle,
                        style = WepliTheme.typo.subTitle7,
                        color = WepliTheme.color.gray600
                    )
                }

                WepliSpacer(horizontal = 8.dp)

                Image(
                    imageVector = ImageVector.vectorResource(videoSizeIcon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onToggleExpanded() }
                )
            }
        }
    }
}


@Composable
fun SongInfoLayout(song: SongUiData) {
    Column {
        Text(
            text = song.title,
            style = WepliTheme.typo.title2.copy(
                fontWeight = FontWeight.Normal
            ),
            color = WepliTheme.color.gray900
        )

        Row(
            modifier = Modifier.padding(top = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = song.artistName,
                style = WepliTheme.typo.body4,
                color = WepliTheme.color.gray700,
            )

            Image(
                painter = painterResource(CoreR.drawable.ic_badge),
                contentDescription = null,
            )
        }

        Text(
            text = song.albumName,
            style = WepliTheme.typo.body3,
            color = WepliTheme.color.gray700,
            modifier = Modifier.padding(top = 12.dp)
        )

        Text(
            text = "FLAC",
            style = WepliTheme.typo.subTitle7,
            color = WepliTheme.color.gray600,
            modifier = Modifier.padding(top = 4.dp)
        )

        ReactionLayout(modifier = Modifier.padding(top = 24.dp))

        Spacer(modifier = Modifier.height(20.dp))

        AsyncImageWithPreview(
            imageUrl = song.getImageUrl(),
            previewImage = painterResource(id = CoreR.drawable.img_placeholder_chuu_2),
            imageOverrideSize = 200.dp,
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(4.dp))
        )
    }
}

@Composable
private fun ReactionLayout(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        LabeledIcon(iconId = CoreR.drawable.ic_heart_vector, text = "10,123")
        LabeledIcon(iconId = CoreR.drawable.ic_comment_vector, text = "10,123")
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = CoreR.drawable.ic_more_dot),
            tint = WepliTheme.color.gray800,
            contentDescription = null
        )
    }
}

@Composable
private fun LabeledIcon(
    @DrawableRes iconId: Int,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = ImageVector.vectorResource(iconId),
            tint = WepliTheme.color.gray800,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = WepliTheme.typo.caption1.copy(
                fontWeight = FontWeight.Medium,
            ),
            color = WepliTheme.color.gray800,
        )
    }
}

@Composable
fun ArtistAlbumGrid(albums: List<AlbumUiData>) {
    Column {
        OneLineTitle(
            title = "이 가수의 다른 앨범",
            showIcon = true,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        ResponsiveAlbumGrid(albums, Modifier.padding(top = 12.dp))
    }
}

@Preview(heightDp = 1000)
@Composable
fun SongInfoScreenPreview() {
    SongInfoScreen(
        state = SongInfoUiState(song = songMockData.random()),
        navOnBack = {},
        sendAction = {},
    )
}