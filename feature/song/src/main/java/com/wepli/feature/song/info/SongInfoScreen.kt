package com.wepli.feature.song.info

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.ScrollableAppBar
import appbar.WepliAppBar
import com.wepli.designsystem.R
import com.wepli.feature.song.info.mvi.SongInfoIntent
import com.wepli.feature.song.info.mvi.SongInfoUiState
import com.wepli.shared.feature.mock.songMockData
import com.wepli.shared.feature.uimodel.album.AlbumUiData
import com.wepli.uimodel.music.SongUiData
import custom.MusicItem
import custom.MusicItemType
import custom.OneLineTitle
import extensions.compose.toPx
import image.AsyncImageWithPreview
import org.orbitmvi.orbit.compose.collectAsState
import theme.WepliTheme

@Preview
@Composable
fun SongInfoScreenPreview() {
    SongInfoScreen(state = SongInfoUiState(song = songMockData.random()), navOnBack = {})
}

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
        navOnBack = navOnBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongInfoScreen(
    state: SongInfoUiState,
    navOnBack: () -> Unit
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
                onClickBack = { },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WepliTheme.color.black)
                .verticalScroll(scrollState)
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            Text(
                text = song.title,
                style = WepliTheme.typo.title2.copy(
                    fontWeight = FontWeight.Normal
                ),
                color = WepliTheme.color.gray900
            )

            Text(
                text = song.artistName,
                style = WepliTheme.typo.body4,
                color = WepliTheme.color.gray700,
                modifier = Modifier.padding(top = 12.dp)
            )

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
                previewImage = painterResource(id = R.drawable.img_placeholder_chuu_2),
                imageOverrideSize = 200.dp,
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(4.dp))
            )

            Spacer(modifier = Modifier.height(64.dp))
            SongDetailInfoLayout(
                composers = song.composers,
                genres = song.genres
            )

            Spacer(modifier = Modifier.height(64.dp))
            AlbumInfoLayout(album = state.album)

            Spacer(modifier = Modifier.height(64.dp))
            SimilarSongsLayout(similarSongs = state.similarSongs)

            Spacer(modifier = Modifier.height(64.dp))
            ResponsiveAlbumGrid(state.artistAlbums)
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalLayoutApi::class)
@Composable
fun ResponsiveAlbumGrid(albums: List<AlbumUiData>) {
    val activity = LocalActivity.current
    val windowSizeClass = activity?.let { calculateWindowSizeClass(it) }

    val spacing = 20.dp
    val itemsPerRow = when (windowSizeClass?.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 2
        WindowWidthSizeClass.Medium -> 3
        WindowWidthSizeClass.Expanded -> 4
        else -> 2
    }

    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val itemWidthPx = (maxWidth.toPx() - spacing.toPx() * (itemsPerRow - 1)) / itemsPerRow
        val itemWidthDp = with(LocalDensity.current) { itemWidthPx.toDp() }

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            maxItemsInEachRow = itemsPerRow,
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            albums.forEach { album ->
                AlbumComponent(
                    album = album,
                    modifier = Modifier.width(itemWidthDp)
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SongDetailInfoLayout(
    composers: List<String>,
    genres: List<String>
) {
    @Composable
    fun TagList(title: String, items: List<String>, modifier: Modifier = Modifier) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = title,
                style = WepliTheme.typo.body4,
                color = WepliTheme.color.gray700,
                modifier = Modifier
                    .width(40.dp)
                    .padding(vertical = 8.dp)
                    .align(Alignment.Top)
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items.forEach {
                    Text(
                        text = it,
                        style = WepliTheme.typo.body5,
                        color = WepliTheme.color.gray700,
                        modifier = Modifier
                            .border(1.dp, WepliTheme.color.gray100, RoundedCornerShape(100.dp))
                            .padding(vertical = 8.dp, horizontal = 12.dp)
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        OneLineTitle(
            title = "곡 정보",
            showIcon = true,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        Column(
            modifier = Modifier.padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            if (composers.isNotEmpty()) {
                TagList("작곡", composers)
            }

            if (genres.isNotEmpty()) {
                TagList("장르", genres)
            }
        }
    }
}

@Composable
private fun AlbumInfoLayout(
    album: AlbumUiData
) {
    @Composable
    fun InfoText(title: String, content: String) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Text(
                text = title,
                style = WepliTheme.typo.body5,
                color = WepliTheme.color.gray600,
                modifier = Modifier.width(40.dp)
            )

            Text(
                text = content,
                style = WepliTheme.typo.body4,
                color = WepliTheme.color.gray800,
            )
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        OneLineTitle(
            title = "앨범 정보",
            showIcon = true,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            InfoText("앨범명", album.name)
            InfoText("발매", album.releaseDate)
            InfoText("유형", if (album.isSingle) "싱글" else "정규 앨범")
            InfoText("저작권", album.copyright)
        }
    }
}

@Composable
private fun SimilarSongsLayout(similarSongs: List<SongUiData>) {
    if (similarSongs.isEmpty()) return
    val imageSize = 52.dp

    Column(modifier = Modifier.fillMaxWidth()) {
        OneLineTitle(
            title = "함께 들으면 좋은 곡",
            showIcon = true,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        similarSongs.forEach {
            MusicItem(
                modifier = Modifier.padding(top = 12.dp),
                imageModifier = Modifier
                    .size(imageSize)
                    .clip(RoundedCornerShape(3.dp)),
                musicItemType = MusicItemType.Normal(it, imageSize.toPx()),
                showMoreIcon = true
            )
        }
    }
}

@Composable
private fun ReactionLayout(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        LabeledIcon(iconId = R.drawable.ic_heart_vector, text = "10,123")
        LabeledIcon(iconId = R.drawable.ic_comment_vector, text = "10,123")
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_more_dot),
            tint = WepliTheme.color.gray800,
            contentDescription = null
        )
    }
}

@Composable
private fun AlbumComponent(
    album: AlbumUiData,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        AsyncImageWithPreview(
            imageUrl = album.getImageUrl(),
            previewImage = painterResource(id = R.drawable.img_placeholder_chuu_2),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(4.dp))
        )

        Row(modifier = Modifier.padding(top = 12.dp)) {
            Text(
                text = album.name,
                style = WepliTheme.typo.body4,
                color = WepliTheme.color.gray900,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_more_dot_vector),
                tint = WepliTheme.color.gray800,
                contentDescription = null
            )
        }

        Text(
            text = album.artistName,
            style = WepliTheme.typo.caption2,
            color = WepliTheme.color.gray600,
            modifier = Modifier.padding(top = 4.dp)
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