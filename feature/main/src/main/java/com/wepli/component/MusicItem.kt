package com.wepli.component

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
import androidx.compose.material3.IconButton
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
import image.AsyncImageWithPreview
import model.music.ChartMusic
import theme.WePLiTheme

@Preview
@Composable
fun MusicItemPreview() {
    MusicItem(
        chartMusic = ChartMusic(
            rank = 100,
            title = "Small girl (feat. 도경수 (D.O)) Small girl (feat. 도경수 (D.O))",
            artist = "이영지",
            album = "Hello",
            albumCoverUrl = "https://image.bugsm.co.kr/artist/images/1000/800100/80010025_100.jpg?version=332223&d=20220330143136"
        )
    )
}

@Composable
fun MusicItem(modifier: Modifier = Modifier, chartMusic: ChartMusic) {
    Row(
        modifier = modifier.height(52.dp),
    ) {
        AsyncImageWithPreview(
            modifier = Modifier.size(52.dp).clip(RoundedCornerShape(3.dp)),
            imageUrl = chartMusic.albumCoverUrl,
            previewImage = painterResource(id = R.drawable.img_placeholder_album_cover),
            imageOverrideSize = 52.dp,
        )

        Text(
            modifier = Modifier
                .padding(top = 9.dp)
                .width(32.dp),
            text = chartMusic.rank.toString(),
            style = WePLiTheme.typo.caption1,
            color = WePLiTheme.color.gray700,
            textAlign = TextAlign.Center
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = chartMusic.title,
                style = WePLiTheme.typo.body4,
                color = WePLiTheme.color.white,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = chartMusic.artist,
                style = WePLiTheme.typo.caption1,
                color = WePLiTheme.color.gray500,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Spacer(modifier = Modifier.size(4.dp))
        IconButton(
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = { /*TODO*/ }
        ) {
            Icon(
                tint = WePLiTheme.color.gray800,
                painter = painterResource(id = R.drawable.ic_play),
                contentDescription = null
            )
        }
    }
}