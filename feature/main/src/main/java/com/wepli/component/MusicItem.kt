package com.wepli.component

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.wepli.wepli.designsystem.R
import extensions.toPx
import model.MusicData
import theme.WePLiTheme

@Preview
@Composable
fun MusicItemPreview() {
    MusicItem(
        musicData = MusicData(
            rank = 100,
            title = "Small girl (feat. 도경수 (D.O)) Small girl (feat. 도경수 (D.O))",
            artist = "이영지",
            album = "Hello",
            albumCoverUrl = "https://image.bugsm.co.kr/artist/images/1000/800100/80010025_100.jpg?version=332223&d=20220330143136"
        )
    )
}

@Composable
fun MusicItem(modifier: Modifier = Modifier, musicData: MusicData) {
    Row(
        modifier = modifier.height(52.dp),
    ) {
        CoverImage(
            imageUrl = musicData.albumCoverUrl,
            previewImage = R.drawable.img_placeholder_album_cover,
            size = 52.dp,
            shape = RoundedCornerShape(3.dp)
        )

        Text(
            modifier = Modifier
                .padding(top = 9.dp)
                .width(32.dp),
            text = musicData.rank.toString(),
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
                text = musicData.title,
                style = WePLiTheme.typo.body4,
                color = WePLiTheme.color.white,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = musicData.artist,
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

@Composable
fun CoverImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    @DrawableRes previewImage: Int,
    size: Dp,
    shape: Shape,
    contentScale: ContentScale = ContentScale.Crop,
) {
    val isInPreview = LocalInspectionMode.current
    val context = LocalContext.current
    val imageSizePx = size.toPx()

    val imageRequest = remember(imageUrl) {
        ImageRequest.Builder(context)
            .data(imageUrl)
            .size(imageSizePx)
            .build()
    }

    if (isInPreview) {
        Image(
            modifier = modifier
                .size(size)
                .clip(shape),
            painter = painterResource(id = previewImage),
            contentDescription = null,
        )
    } else {
        SubcomposeAsyncImage(
            modifier = modifier
                .size(size)
                .clip(shape),
            model = imageRequest,
            contentScale = contentScale,
            loading = {
                Box(modifier = Modifier.size(16.dp)) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            },
            contentDescription = null
        )
    }
}