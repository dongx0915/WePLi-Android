package custom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wepli.designsystem.R
import com.wepli.uimodel.music.SongUiData
import image.AsyncImageWithPreview
import theme.WepliTheme

@Composable
fun SongItem(song: SongUiData) {
    Column(
        modifier = Modifier.width(92.dp)
    ) {
        Box(modifier = Modifier) {
            AsyncImageWithPreview(
                modifier = Modifier.size(92.dp).clip(RoundedCornerShape(4.dp)),
                imageUrl = song.coverImg,
                previewImage = painterResource(id = R.drawable.img_placeholder_album_cover),
                imageOverrideSize = 92.dp,
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(4.dp),
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_play),
                    tint = WepliTheme.color.gray900,
                    contentDescription = null
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = song.title,
            style = WepliTheme.typo.body5,
            color = WepliTheme.color.white,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = song.artistName,
            style = WepliTheme.typo.caption1,
            color = WepliTheme.color.gray500,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
fun SongItemPreview() {
    SongItem(
        song = SongUiData(
            id = "0",
            title = "비가 내리는 날에는",
            artistName = "윤하",
            albumName = "A Perfect Day to Say I Love You",
            coverImg = "https://image.bugsm.co.kr/artist/images/1000/800100/80010025_100.jpg?version=332223&d=20220330143136",
            href = "https://music.apple.com/kr/song/1",
            genres = listOf("Ballad", "K-Pop"),
            durationMillis = 230000L
        )
    )
}