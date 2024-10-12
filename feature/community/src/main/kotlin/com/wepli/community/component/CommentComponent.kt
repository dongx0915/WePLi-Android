package com.wepli.community.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wepli.designsystem.R
import extensions.getRelativeTime
import image.AsyncImageWithPreview
import theme.WePLiTheme
import java.text.NumberFormat
import java.util.Date


@Preview
@Composable
fun CommentPreview() {
    val nickname = "nkjllksdk"
    val content = "처음 들어보는데 완전 봄 느낌 물씬나네요! 기분 전환도 되고 화창한 봄날 거리를 걷는 기분이 들어요.\n좋은 곡 공유해줘서 감사합니다!"
    CommentItem(
        nickname = nickname,
        profileImg = "https://image.bugsm.co.kr/artist/images/1000/800491/80049126_068.jpg?version=301040&d=20210325154659",
        content = content,
        likeCount = 12345,
        commentCreatedDate = Date()
    )
}

@Composable
fun CommentItem(
    modifier: Modifier = Modifier,
    nickname: String,
    profileImg: String,
    content: String,
    likeCount: Int,
    commentCreatedDate: Date,
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        AsyncImageWithPreview(
            modifier = Modifier
                .size(32.dp)
                .clip(shape = CircleShape),
            imageUrl = profileImg,
            previewImage = painterResource(id = R.drawable.img_placeholder_minnie),
            imageOverrideSize = 32.dp
        )

        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // 댓글 헤더 영역
            CommentHeader(nickname, commentCreatedDate)

            Spacer(modifier = Modifier.height(4.dp))
            // 댓글 콘텐츠 영역
            CommentBody(content)

            Spacer(modifier = Modifier.height(8.dp))
            // 댓글 푸터 영역
            CommentFooter(likeCount)
        }
    }
}

@Composable
private fun CommentHeader(
    nickname: String,
    createAt: Date,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = nickname,
            style = WePLiTheme.typo.body5,
            color = WePLiTheme.color.white
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = createAt.getRelativeTime(),
            style = WePLiTheme.typo.caption2,
            color = WePLiTheme.color.gray600
        )
    }
}

@Composable
private fun CommentBody(
    content: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = content,
            style = WePLiTheme.typo.body5,
            color = WePLiTheme.color.white
        )
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_heart),
            tint = WePLiTheme.color.gray800,
            contentDescription = null
        )
    }
}

@Composable
private fun CommentFooter(likeCount: Int) {
    val likes = NumberFormat.getNumberInstance().format(likeCount)

    Row{
        Text(
            text = "좋아요 %s개".format(likes),
            style = WePLiTheme.typo.body6,
            color = WePLiTheme.color.gray500
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "답글 달기",
            style = WePLiTheme.typo.body6,
            color = WePLiTheme.color.gray500
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "삭제",
            style = WePLiTheme.typo.body6,
            color = WePLiTheme.color.gray600
        )
    }
}