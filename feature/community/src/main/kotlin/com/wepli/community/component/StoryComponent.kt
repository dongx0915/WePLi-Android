package com.wepli.community.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wepli.community.mock.userMockData
import com.wepli.designsystem.R
import image.AsyncImageWithPreview
import model.user.User
import theme.WePLiTheme

@Preview
@Composable
fun StoryPreview() {
    Column {
        WePLiStoryLayout(users = userMockData)
        Spacer(modifier = Modifier.height(12.dp))
        StoryItem(nickname = "chuuuo3o__", profileImgUrl = "")
    }
}

@Composable
fun WePLiStoryLayout(
    modifier: Modifier = Modifier,
    users: List<User>,
) {
    Column(
        modifier = modifier
    ) {
        LazyRow(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            items(items = users, key = { user -> user.nickname }) { user ->
                StoryItem(user.nickname, user.profileImgUrl)
            }
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = WePLiTheme.color.gray050,
        )
    }
}

@Composable
fun StoryItem(
    nickname: String,
    profileImgUrl: String,
) {
    val imageSize: Dp = 52.dp
    val storyShape: Shape = RoundedCornerShape(20.dp)

    Column(
        modifier = Modifier.width(imageSize),
    ) {
        AsyncImageWithPreview(
            modifier = Modifier
                .size(imageSize)
                .clip(storyShape)
                .border(brush = WePLiTheme.color.linear3, shape = storyShape, width = (1.5).dp),
            imageUrl = profileImgUrl,
            previewImage = painterResource(id = R.drawable.img_placeholder_chuu),
            imageOverrideSize = imageSize,
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = nickname,
            style = WePLiTheme.typo.caption2,
            color = WePLiTheme.color.white,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}