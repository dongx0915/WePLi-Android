package com.wepli.playlist.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wepli.shared.feature.mock.songMockData
import com.wepli.uimodel.artist.ArtistUiData
import custom.ArtistProfileListItem
import custom.MusicItem
import custom.MusicItemType
import custom.TwoLineTitle

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

@Preview
@Composable
fun PlaylistBsideTrackContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        songMockData.forEachIndexed { index, song ->
            MusicItem(
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .padding(start = 20.dp, end = 16.dp),
                musicItemType = MusicItemType.Normal(song),
                showMoreIcon = true
            )
        }
    }
}