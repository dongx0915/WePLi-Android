package com.community.wepli.community.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.community.wepli.community.mock.posts
import com.wepli.designsystem.R
import custom.SongItem
import image.AsyncImageWithPreview
import model.music.Song
import theme.WePLiTheme
import util.OrientationPreviews

@OrientationPreviews
@Composable
fun PostPreview() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.verticalScroll(scrollState),
    ) {
        posts.forEach { post ->
            PostItem(
                title = post.title,
                content = post.content,
                nickname = post.author,
                profileImageUrl = post.profileImg,
                songList = post.songList
            )
        }
    }
}

@Composable
fun PostItem(
    title: String,
    content: String,
    nickname: String,
    profileImageUrl: String,
    songList: List<Song>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = WePLiTheme.color.black)
    ) {
        // 게시글 헤더
        PostHeader(nickname, profileImageUrl)

        // 게시글 본문
        PostContent(title, content)

        PostSongList(songList = songList)

        // 좋아요, 댓글, 저장
        PostFooter(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )

        HorizontalDivider(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 10.dp),
            color = WePLiTheme.color.gray050,
        )
    }
}

@Composable
fun PostHeader(nickname: String, profileImageUrl: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 4.dp)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImageWithPreview(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            imageUrl = profileImageUrl,
            previewImage = painterResource(id = R.drawable.img_placeholder_artist_profile),
            imageOverrideSize = 32.dp,
        )

        Spacer(modifier = Modifier.width(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.alignByBaseline(),
                text = nickname,
                style = WePLiTheme.typo.subTitle5.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = WePLiTheme.color.white,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.alignByBaseline(),
                text = "팔로우",
                style = WePLiTheme.typo.caption1.copy(
                    brush = WePLiTheme.color.linear3
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = { /*TODO*/ }
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_more_dot),
                tint = WePLiTheme.color.gray700,
                contentDescription = null
            )
        }
    }
}

@Composable
fun PostContent(title: String, content: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 4.dp, bottom = 20.dp)
    ) {
        Text(
            text = title,
            style = WePLiTheme.typo.subTitle1,
            color = WePLiTheme.color.white
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = content,
            style = WePLiTheme.typo.body4,
            color = WePLiTheme.color.gray700
        )
    }
}

@Composable
fun PostFooter(modifier: Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        LabeledIcon(icon = painterResource(id = R.drawable.ic_heart), text = "10,123")
        LabeledIcon(icon = painterResource(id = R.drawable.ic_comment), text = "10,123")
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_bookmark),
            tint = WePLiTheme.color.gray800,
            contentDescription = null
        )
    }
}

@Composable
fun PostSongList(songList: List<Song>) {
    when {
        songList.size > 1 -> { // 노래 여러개인 경우
            LazyRow(
                modifier = Modifier.padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 20.dp),
            ) {
                items(songList) { song ->
                    SongItem(song)
                }
            }
        }
        songList.size == 1 -> { // 노래 1개인 경우
            SingleSongItem(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 20.dp),
                song = songList.first()
            )
        }
        else -> Unit
    }
}

@Composable
fun SingleSongItem(
    modifier: Modifier = Modifier,
    song: Song,
) {
    Row(
        modifier = modifier
            .border(1.dp, WePLiTheme.color.linear3, RoundedCornerShape(50.dp))
            .background(
                brush = WePLiTheme.color.linear3,
                alpha = 0.2f,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(vertical = 4.dp)
            .padding(start = 8.dp, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${song.title} - ${song.artist}",
            style = WePLiTheme.typo.caption1,
            color = WePLiTheme.color.white,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(id = R.drawable.ic_play_gradient),
            tint = Color.Unspecified,
            contentDescription = null,
        )
    }
}

@Composable
fun LabeledIcon(
    icon: Painter,
    text: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = icon,
            tint = WePLiTheme.color.gray800,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = WePLiTheme.typo.caption1.copy(
                fontWeight = FontWeight.Medium,
            ),
            color = WePLiTheme.color.gray800,
        )
    }
}