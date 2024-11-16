package com.wepli.community.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.ScrollableAppBar
import appbar.WePLiAppBar
import com.wepli.community.component.CommentItem
import com.wepli.community.component.PostItem
import com.wepli.shared.feature.mock.commentMockData
import com.wepli.shared.feature.uimodel.community.PostUiData
import com.wepli.uimodel.community.CommentUiData
import theme.WePLiTheme

@Preview
@Composable
fun CommunityDetailScreenPreview() {
    CommunityDetailScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityDetailScreen(
    viewModel: CommunityDetailViewModel = hiltViewModel(),
    navOnBack: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    val post = state.post
    val scrollState = rememberLazyListState()
    val comments = remember {
        commentMockData().sortedByDescending { it.createdAt }
    }

    ScrollableAppBar(
        scrollState = scrollState,
        backgroundColors = Color.Transparent to WePLiTheme.color.black,
        contentsColors = Color.White to Color.White,
        topBarComponent = { backgroundColor, contentsColor, _, _ ->
            WePLiAppBar(
                title = post.title,
                containerColor = backgroundColor,
                contentsColor = contentsColor,
                showBackButton = true,
                onClickBack = { navOnBack() }
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
            item { PostContent(post) }

            items(comments) { comment ->
                CommentContent(comment)
            }
        }
    }
}

@Composable
fun PostContent(post: PostUiData) {
    PostItem(
        title = post.title,
        content = post.content,
        nickname = post.author,
        profileImageUrl = post.profileImg,
        songList = post.songList
    )
}

@Composable
fun CommentContent(comment: CommentUiData) {
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