package com.wepli.feature.song.info.component.song

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.wepli.uimodel.music.SongUiData
import custom.MusicItem
import custom.MusicItemType
import custom.OneLineTitle
import compose.toPx

@Composable
internal fun SimilarSongsLayout(similarSongs: List<SongUiData>) {
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