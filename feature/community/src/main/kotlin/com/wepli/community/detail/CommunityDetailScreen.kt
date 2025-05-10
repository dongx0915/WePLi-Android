package com.wepli.community.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.ScrollableAppBar
import appbar.WepliAppBar
import com.wepli.community.component.CommentItem
import com.wepli.community.component.PostItem
import com.wepli.community.detail.state.CommunityDetailIntent
import com.wepli.community.detail.state.CommunityDetailState
import com.wepli.designsystem.R
import com.wepli.shared.feature.mock.commentMockData
import com.wepli.shared.feature.uimodel.community.CommentUiData
import com.wepli.shared.feature.uimodel.community.PostUiData
import image.AsyncImageWithPreview
import org.orbitmvi.orbit.compose.collectAsState
import textfield.WepliTextField
import textfield.WepliTextFieldType
import theme.WepliTheme

@Preview
@Composable
fun CommunityDetailScreenPreview() {
    CommunityDetailScreen(
        state = CommunityDetailState(),
        sendAction = {},
        navOnBack = {}
    )
}

@Composable
fun CommunityDetailScreenRoute(
    post: PostUiData,
    navOnBack: () -> Unit = {},
) {
    val viewModel: CommunityDetailViewModel = hiltViewModel()
    val state: CommunityDetailState by viewModel.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.processIntent(CommunityDetailIntent.InitPost(post))
    }

    CommunityDetailScreen(
        state = state,
        sendAction = viewModel::processIntent,
        navOnBack = { }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CommunityDetailScreen(
    state: CommunityDetailState,
    sendAction: (CommunityDetailIntent) -> Unit,
    navOnBack: () -> Unit,
) {
    val post = state.post
    val scrollState = rememberLazyListState()
    val comments = remember {
        commentMockData().sortedByDescending { it.createdAt }
    }

    ScrollableAppBar(
        scrollState = scrollState,
        backgroundColors = Color.Transparent to WepliTheme.color.black,
        contentsColors = Color.White to Color.White,
        topBarComponent = { backgroundColor, contentsColor, isFullScrolled, _ ->
            WepliAppBar(
                title = if (isFullScrolled) post.title else "",
                containerColor = backgroundColor,
                contentsColor = contentsColor,
                showBackButton = true,
                onClickBack = { navOnBack() }
            )
        }
    ) { paddingValue ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(WepliTheme.color.black)
                .padding(paddingValue)
                .consumeWindowInsets(paddingValue)
                .imePadding(),
            contentAlignment = Alignment.BottomCenter
        ) {
            LazyColumn(
                state = scrollState,
                contentPadding = PaddingValues(bottom = 72.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter),
            ) {
                item { PostContent(post) }

                items(comments) { comment ->
                    CommentContent(comment)
                }
            }

            CommentTextFieldLayout(
                userProfileImgUrl = state.user.profileImgUrl
            )
        }
    }
}

@Composable
fun CommentTextFieldLayout(
    userProfileImgUrl: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .background(WepliTheme.color.black)
            .padding(vertical = 8.dp, horizontal = 20.dp)
    ) {
        AsyncImageWithPreview(
            modifier = Modifier
                .size(32.dp)
                .clip(shape = CircleShape),
            imageUrl = userProfileImgUrl,
            previewImage = painterResource(id = R.drawable.img_placeholder_minnie),
            imageOverrideSize = 32.dp
        )

        WepliTextField(
            value = "",
            onValueChanged = { newValue ->
                // sendAction(PhotoCardDetailIntent.OnChangedInstagramId(newValue))
            },
            singleLine = true,
            placeholder = "댓글을 남겨주세요",
            type = WepliTextFieldType.Normal,
            modifier = Modifier.clip(RoundedCornerShape(16.dp))
        )
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