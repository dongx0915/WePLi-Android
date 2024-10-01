package com.wepli.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wepli.wepli.designsystem.R
import image.AsyncImageWithPreview
import model.RecommendPlaylist
import theme.WePLiTheme

@Composable
fun PlayListCoverItem(recommendPlaylist: RecommendPlaylist) {
    Column(modifier = Modifier.width(136.dp)) {
        AsyncImageWithPreview(
            imageUrl = recommendPlaylist.coverImgUrl,
            previewImage = painterResource(id = R.drawable.img_placeholder_album_cover),
            size = 136.dp,
            shape = RoundedCornerShape(4.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = recommendPlaylist.title,
            style = WePLiTheme.typo.body5,
            color = WePLiTheme.color.white,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
fun PlaylistCoverPreview() {
    PlayListCoverItem(
        recommendPlaylist = RecommendPlaylist(
            title = "끈적달달한 체리위스키를 머금은 힙합 R&B 두 줄 넘어가면",
            coverImgUrl = "https://image.bugsm.co.kr/artist/images/1000/800100/80010025_100.jpg?version=332223&d=20220330143136"
        )
    )
}