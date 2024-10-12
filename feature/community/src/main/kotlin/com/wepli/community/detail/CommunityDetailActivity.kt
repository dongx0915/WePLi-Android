package com.wepli.community.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import appbar.ScrollableAppBar
import appbar.WePLiAppBar
import com.wepli.community.component.CommentItem
import com.wepli.community.component.PostItem
import com.wepli.community.mock.commentMockData
import com.wepli.community.mock.postMockData
import theme.WePLiTheme

class CommunityDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WePLiTheme {
                CommunityDetailScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CommunityDetailScreen() {
    val scrollState = rememberLazyListState()
    val posts = List(100) { postMockData }.flatten()
    val comments = remember {
        commentMockData().sortedByDescending { it.createdAt }
    }

    ScrollableAppBar(
        scrollState = scrollState,
        backgroundColors = Color.Transparent to WePLiTheme.color.black,
        contentsColors = Color.White to Color.White,
        topBarComponent = { backgroundColor, contentsColor ->
            WePLiAppBar(
                title = posts[0].title,
                containerColor = backgroundColor,
                contentsColor = contentsColor,
                showBackButton = true,
            )
        }
    ) { paddingValue ->
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
                .background(WePLiTheme.color.black),
            contentPadding = paddingValue
        ) {
            item {
                val post = posts[0]
                PostItem(
                    title = post.title,
                    content = post.content,
                    nickname = post.author,
                    profileImageUrl = post.profileImg,
                    songList = post.songList
                )
            }

            items(comments) { comment ->
                CommentItem(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .padding(horizontal = 20.dp),
                    nickname = comment.nickname,
                    profileImg = comment.profileImg,
                    content = comment.content,
                    likeCount = comment.likeCount,
                    commentCreatedDate = comment.createdAt
                )
            }
        }
    }
}

