package com.wepli.feature.song.info.component.album

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wepli.shared.feature.mock.songMockData
import com.wepli.shared.feature.uimodel.album.AlbumUiData
import image.AsyncImageWithPreview
import theme.WepliTheme
import com.wepli.designsystem.R as DesignSystemR

@Composable
internal fun AlbumComponent(
    album: AlbumUiData,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        AsyncImageWithPreview(
            imageUrl = album.getImageUrl(),
            previewImage = painterResource(id = DesignSystemR.drawable.img_placeholder_chuu_2),
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
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = ImageVector.vectorResource(DesignSystemR.drawable.ic_more_dot_vector),
                tint = WepliTheme.color.gray800,
                contentDescription = null
            )
        }

        Text(
            text = album.artistName,
            style = WepliTheme.typo.caption2,
            color = WepliTheme.color.gray600,
            maxLines = 1,
            modifier = Modifier.padding(top = 4.dp)
        )

        Text(
            text = "${album.releaseDate} • ${album.albumType}",
            style = WepliTheme.typo.body6,
            color = WepliTheme.color.gray600,
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}

@Preview
@Composable
fun AlbumComponentPreview() {
    AlbumComponent(
        album = AlbumUiData(
            id = "album_001",
            href = "https://api.example.com/albums/album_001",
            name = "Mockingbird Melodies",
            description = "A soulful collection of mellow acoustic tracks that touch the heart.",
            coverImg = "https://example.com/images/albums/album_001_cover.jpg",
            albumUrl = "https://example.com/albums/album_001",
            isSingle = false,
            artistId = "artist_001",
            artistName = "Jane Doe",
            releaseDate = "2024-12-01",
            copyright = "© 2024 Mock Records",
            trackCount = 3,
            tracks = songMockData.take(3),
            genres = listOf("Acoustic", "Indie", "Chill")
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    )
}