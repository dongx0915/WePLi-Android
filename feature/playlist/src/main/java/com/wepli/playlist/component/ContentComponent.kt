package com.wepli.playlist.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wepli.shared.feature.mock.artistMockData
import com.wepli.shared.feature.mock.songMockData
import com.wepli.uimodel.artist.ArtistUiData
import com.wepli.uimodel.music.SongUiData
import custom.ArtistProfileListItem
import custom.MusicItem
import custom.MusicItemType
import custom.TwoLineTitle
import extensions.compose.toPx

@Composable
fun ArtistLayout(
    modifier: Modifier = Modifier,
    title: String,
    subscription: String,
    artistList: List<ArtistUiData>
) {
    if (artistList.isEmpty()) return

    Column(modifier = modifier) {
        TwoLineTitle(
            title = title,
            subscription = subscription
        )
        Spacer(modifier = Modifier.height(4.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(start = 20.dp)
        ) {
            items(artistList) { artist ->
                ArtistProfileListItem(artist)
            }
        }
    }
}

@Composable
fun PlaylistBsideTrackContent(
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
                modifier = Modifier
                    .padding(start = 20.dp, end = 16.dp),
                imageModifier = Modifier
                    .size(imageSize)
                    .clip(RoundedCornerShape(3.dp)),
                musicItemType = MusicItemType.Normal(song, imageSize.toPx()),
                showMoreIcon = true
            )
        }
    }
}

@Preview
@Composable
fun PlaylistBSideTrackContentPreview() {
    PlaylistBsideTrackContent(
        bSideTrack = songMockData
    )
}

@Preview
@Composable
fun ArtistLayoutPreview() {
    ArtistLayout(
        title = "아티스트",
        subscription = "플레이리스트를 빛낸 아티스트들이에요",
        artistList = artistMockData
    )
}