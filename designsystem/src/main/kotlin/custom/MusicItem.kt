package custom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wepli.designsystem.R
import com.wepli.uimodel.music.ChartMusicUiData
import com.wepli.uimodel.music.SongUiData
import image.AsyncImageWithPreview
import theme.WepliTheme

sealed interface MusicItemType {
    val title: String
    val artist: String
    val coverImgUrl: String

    data class Chart(val chartMusic: ChartMusicUiData) : MusicItemType {
        override val title: String = chartMusic.title
        override val artist: String = chartMusic.artist
        override val coverImgUrl: String = chartMusic.albumCoverUrl
    }

    data class Normal(val songUiData: SongUiData) : MusicItemType {
        override val title: String = songUiData.title
        override val artist: String = songUiData.artist
        override val coverImgUrl: String = songUiData.albumCoverImg
    }
}

@Preview
@Composable
fun MusicItemPreview() {
    Column {
        MusicItem(
            musicItemType = MusicItemType.Chart(
                chartMusic = ChartMusicUiData(
                    rank = 100,
                    title = "title",
                    artist = "artist",
                    album = "album",
                    albumCoverUrl = "https://via.placeholder.com/150",
                )
            ),
            showPlayIcon = true,
            showMoreIcon = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        MusicItem(
            musicItemType = MusicItemType.Normal(
                songUiData = SongUiData(
                    title = "title",
                    artist = "artist",
                    album = "album",
                    albumCoverImg = "https://via.placeholder.com/150",
                )
            ),
            showPlayIcon = true,
            showMoreIcon = true
        )
    }
}


@Composable
fun MusicItem(
    modifier: Modifier = Modifier,
    musicItemType: MusicItemType,
    showPlayIcon: Boolean = false,
    showMoreIcon: Boolean = false,
) {
    Row(
        modifier = modifier.height(52.dp),
    ) {
        AsyncImageWithPreview(
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(3.dp)),
            imageUrl = musicItemType.coverImgUrl,
            previewImage = painterResource(id = R.drawable.img_placeholder_album_cover),
            imageOverrideSize = 52.dp,
        )

        when (musicItemType) {
            is MusicItemType.Chart -> {
                Text(
                    modifier = Modifier
                        .padding(top = 9.dp)
                        .width(32.dp),
                    text = musicItemType.chartMusic.rank.toString(),
                    style = WepliTheme.typo.caption1,
                    color = WepliTheme.color.gray700,
                    textAlign = TextAlign.Center
                )
            }

            is MusicItemType.Normal -> {
                Spacer(modifier = Modifier.width(14.dp))
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = musicItemType.title,
                style = WepliTheme.typo.body4,
                color = WepliTheme.color.white,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = musicItemType.artist,
                style = WepliTheme.typo.caption1,
                color = WepliTheme.color.gray500,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        if (showPlayIcon) {
            Spacer(modifier = Modifier.size(4.dp))
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(24.dp),
                tint = WepliTheme.color.gray800,
                painter = painterResource(id = R.drawable.ic_play),
                contentDescription = null
            )
        }

        if (showMoreIcon) {
            Spacer(modifier = Modifier.size(4.dp))
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(24.dp),
                tint = WepliTheme.color.gray800,
                painter = painterResource(id = R.drawable.ic_more_dot),
                contentDescription = null
            )
        }
    }
}