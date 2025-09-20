package com.wepli.home.screen.relaylist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wepli.home.component.PlayListCoverItem
import compose.MeasuredHeightContainer
import custom.OneLineTitle
import model.playlist.RecommendPlaylist

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
