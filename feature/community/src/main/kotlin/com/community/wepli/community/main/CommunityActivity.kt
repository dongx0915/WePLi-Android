package com.community.wepli.community.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.WePLiAppBar
import com.community.wepli.community.main.state.CommunityMainState
import com.community.wepli.community.mock.postMockData
import com.community.wepli.community.mock.userMockData
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
                CommunityScreen(state)
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(
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
        state = CommunityMainState(
            storyUsers = userMockData,
            posts = postMockData
        )
    )
}