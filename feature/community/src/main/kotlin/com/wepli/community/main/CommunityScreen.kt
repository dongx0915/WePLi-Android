package com.wepli.community.main

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.AppBarIcon
import appbar.AppBarIconType
import appbar.WePLiAppBar
import com.wepli.community.component.PostItem
import com.wepli.community.component.WePLiStoryLayout
import com.wepli.shared.feature.uimodel.user.UserUiData
import com.wepli.shared.feature.uimodel.community.PostUiData
import theme.WepliTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(
    viewModel: CommunityViewModel = hiltViewModel(),
    navOnCommunityDetail: (PostUiData) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val storyUsers: List<UserUiData> by rememberUpdatedState(newValue = state.storyUsers)
    val posts: List<PostUiData> by rememberUpdatedState(newValue = state.posts)

    Scaffold(
        containerColor = WepliTheme.color.black,
        topBar = {
            WePLiAppBar(
                showLogo = true,
                showBackButton = false,
                actionIcons = listOf {
                    AppBarIcon(icon = AppBarIconType.Search())
                    AppBarIcon(icon = AppBarIconType.Notification())
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
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


@Preview
@Composable
fun CommunityScreenPreview() {
    CommunityScreen()
}