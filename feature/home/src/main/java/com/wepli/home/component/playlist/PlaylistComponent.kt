package com.wepli.home.component.playlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wepli.core.resources.R as CoreR
import com.wepli.shared.feature.mock.recommendPlaylistMockData
import compose.MeasuredHeightContainer
import custom.OneLineTitle
import image.AsyncImageWithPreview
import model.playlist.RecommendPlaylist
import theme.WepliTheme

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
fun PlayListCoverItem(
    modifier: Modifier = Modifier,
    recommendPlaylist: RecommendPlaylist,
) {
    Column(modifier = modifier.width(136.dp)) {
        AsyncImageWithPreview(
            modifier = Modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(4.dp)),
            imageUrl = recommendPlaylist.coverImgUrl,
            previewImage = painterResource(id = CoreR.drawable.img_placeholder_album_cover),
            imageOverrideSize = 136.dp,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = recommendPlaylist.title,
            style = WepliTheme.typo.body5,
            color = WepliTheme.color.white,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
fun PlaylistCoverPreview() {
    PlayListCoverItem(
        recommendPlaylist = recommendPlaylistMockData[0].copy(
            title = "끈적달달한 체리위스키를 머금은 힙합 R&B 두 줄 넘어가면"
        )
    )
}