package com.wepli.feature.song.info.component.album

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wepli.shared.feature.uimodel.album.AlbumUiData
import custom.OneLineTitle
import theme.WepliTheme

@Composable
internal fun AlbumInfoLayout(
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
            InfoText("유형", album.albumType)
            InfoText("저작권", album.copyright)
        }
    }
}