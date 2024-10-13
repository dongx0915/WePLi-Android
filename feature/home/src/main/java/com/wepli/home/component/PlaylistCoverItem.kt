package com.wepli.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wepli.designsystem.R
import com.wepli.shared.feature.mock.recommendPlaylistMockData
import image.AsyncImageWithPreview
import model.playlist.RecommendPlaylist
import theme.WePLiTheme

@Composable
fun PlayListCoverItem(
    modifier: Modifier = Modifier,
    recommendPlaylist: RecommendPlaylist,
) {
    Column(modifier = modifier.width(136.dp)) {
        AsyncImageWithPreview(
            modifier = Modifier.clip(RoundedCornerShape(4.dp)),
            imageUrl = recommendPlaylist.coverImgUrl,
            previewImage = painterResource(id = R.drawable.img_placeholder_album_cover),
            imageOverrideSize = 136.dp,
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
        recommendPlaylist = recommendPlaylistMockData[0].copy(
            title = "끈적달달한 체리위스키를 머금은 힙합 R&B 두 줄 넘어가면"
        )
    )
}