package com.wepli.community.main.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.AppBarIcon
import appbar.AppBarIconType
import appbar.WepliAppBar
import com.wepli.community.component.PostItem
import com.wepli.community.component.WePLiStoryLayout
import com.wepli.community.main.mvi.CommunityMainEffect
import com.wepli.community.main.mvi.CommunityMainIntent
import com.wepli.community.main.mvi.CommunityMainUiState
import com.wepli.community.main.viewmodel.CommunityViewModel
import com.wepli.designsystem.R
import com.wepli.shared.feature.mock.postMockData
import com.wepli.shared.feature.mock.userMockData
import com.wepli.shared.feature.uimodel.user.UserUiData
import com.wepli.shared.feature.uimodel.community.PostUiData
import common.WepliSpacer
import dev.chrisbanes.haze.hazeSource
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import theme.LocalHazeState
import theme.WepliTheme

@Composable
fun CommunityMainScreenRoute(
    needRefresh: Boolean,
    navOnCommunityDetail: (PostUiData) -> Unit,
    navOnCommunityWrite: () -> Unit,
) {
    val viewModel: CommunityViewModel = hiltViewModel()
    val state: CommunityMainUiState by viewModel.collectAsState()
    val context = LocalContext.current

    viewModel.collectSideEffect {
        when (it) {
            is CommunityMainEffect.ErrorLoadPosts -> {
                Toast.makeText(context, "게시글 조회에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(needRefresh) {
        if (needRefresh) {
            viewModel.processIntent(CommunityMainIntent.LoadPosts)
        }
    }

    CommunityScreen(
        state = state,
        navOnCommunityDetail = navOnCommunityDetail,
        navOnCommunityWrite = navOnCommunityWrite,
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(
    state: CommunityMainUiState,
    navOnCommunityDetail: (PostUiData) -> Unit = {},
    navOnCommunityWrite: () -> Unit = {},
) {
    val storyUsers: List<UserUiData> by rememberUpdatedState(newValue = state.storyUsers)
    val posts: List<PostUiData> by rememberUpdatedState(newValue = state.posts)
    val blurState = LocalHazeState.current

    // 전체를 Box로 감싼 후 hazeSource를 적용해야 바텀 네비바에 블러 적용됨
    Box(modifier = Modifier.fillMaxSize().hazeSource(blurState)) {
        Scaffold(
            containerColor = WepliTheme.color.black,
            topBar = {
                WepliAppBar(
                    showLogo = true,
                    showBackButton = false,
                    actionIcons = listOf {
                        AppBarIcon(icon = AppBarIconType.Search())
                        AppBarIcon(icon = AppBarIconType.Notification())
                    }
                )
            },
            floatingActionButton = {
                PostWritingButton(
                    onClick = { navOnCommunityWrite() },
                )
            },
        ) { paddingValues ->
            val bottomPadding = remember { paddingValues.calculateBottomPadding() * 2 }

            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(bottom = bottomPadding),
            ) {
                item { WePLiStoryLayout(users = storyUsers) }

                items(posts) { post: PostUiData ->
                    PostItem(
                        modifier = Modifier.clickable {
                            navOnCommunityDetail.invoke(post)
                        },
                        title = post.title,
                        content = post.content,
                        nickname = post.author,
                        profileImageUrl = post.profileImg,
                        songList = post.songList
                    )
                }
            }
        }
    }

}

@Composable
fun PostWritingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        Image(
            modifier = modifier
                .clickable { onClick() }
                .size(50.dp),
            painter = painterResource(id = R.drawable.img_fab_write_post),
            contentDescription = ""
        )
        WepliSpacer(vertical = 56.dp)
    }
}

@Preview
@Composable
fun CommunityScreenPreview() {
    CommunityScreen(
        state = CommunityMainUiState(
            storyUsers = userMockData,
            posts = postMockData,
        ),
    )
}