package com.wepli.community.main

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.WePLiAppBar
import com.wepli.community.component.PostItem
import com.wepli.community.component.WePLiStoryLayout
import com.wepli.community.detail.CommunityDetailActivity
import com.wepli.community.main.state.CommunityMainState
import com.wepli.community.mock.postMockData
import com.wepli.community.mock.userMockData
import common.startActivityWithAnimation
import dagger.hilt.android.AndroidEntryPoint
import extensions.setStatusBarColor
import model.community.Post
import model.user.User
import theme.WePLiTheme

@AndroidEntryPoint
class CommunityActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStatusBarColor(Color.Black, darkIcons = false)
        setContent {
            val viewModel: CommunityViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()

            WePLiTheme {
                CommunityScreen(this, state)
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(
    activity: Activity,
    state: CommunityMainState,
) {
    val storyUsers: List<User> by rememberUpdatedState(newValue = state.storyUsers)
    val posts: List<Post> by rememberUpdatedState(newValue = state.posts)

    Scaffold(
        containerColor = WePLiTheme.color.black,
        topBar = {
            WePLiAppBar(
                showLogo = true,
                showBackButton = false,
                showSearchButton = true,
                showNotificationButton = true,
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
        ) {
            item { WePLiStoryLayout(users = storyUsers) }

            items(posts) { post ->
                PostItem(
                    modifier = Modifier.clickable {
                        activity.startActivityWithAnimation<CommunityDetailActivity>()
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
    CommunityScreen(
        activity = Activity(),
        state = CommunityMainState(
            storyUsers = userMockData,
            posts = postMockData
        )
    )
}