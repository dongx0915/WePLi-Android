package com.wepli.community.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.AppBarIcon
import appbar.WePLiAppBar
import com.wepli.community.component.PostItem
import com.wepli.community.component.WePLiStoryLayout
import com.wepli.community.detail.CommunityDetailActivity
import com.wepli.community.main.state.CommunityMainState
import com.wepli.designsystem.R
import com.wepli.navigator.extras.Extras
import com.wepli.navigator.feature.community.CommunityDetailNavigator
import com.wepli.shared.feature.mock.postMockData
import com.wepli.shared.feature.mock.userMockData
import com.wepli.shared.feature.uimodel.user.UserUiData
import com.wepli.uimodel.community.PostUiData
import common.startActivityWithAnimation
import dagger.hilt.android.AndroidEntryPoint
import extensions.setStatusBarColor
import theme.WePLiTheme
import javax.inject.Inject

@AndroidEntryPoint
class CommunityActivity : ComponentActivity() {

    @Inject
    lateinit var communityDetailNavigator: CommunityDetailNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStatusBarColor(Color.Black, darkIcons = false)
        setContent {
            val viewModel: CommunityViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()

            WePLiTheme {
                CommunityScreen(
                    this,
                    communityDetailNavigator,
                    state
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(
    activity: Activity,
    communityDetailNavigator: CommunityDetailNavigator,
    state: CommunityMainState,
) {
    val storyUsers: List<UserUiData> by rememberUpdatedState(newValue = state.storyUsers)
    val posts: List<PostUiData> by rememberUpdatedState(newValue = state.posts)

    Scaffold(
        containerColor = WePLiTheme.color.black,
        topBar = {
            WePLiAppBar(
                showLogo = true,
                showBackButton = false,
                actionIcons = listOf {
                    AppBarIcon(
                        modifier = Modifier.offset(x = (-6).dp),
                        iconResource = R.drawable.ic_search,
                        iconColor = WePLiTheme.color.white,
                        onClick = { }
                    )
                    AppBarIcon(
                        modifier = Modifier.offset(x = (-6).dp),
                        iconResource = R.drawable.ic_alarm,
                        iconColor = WePLiTheme.color.white,
                        onClick = { },
                        badgeVisible = true
                    )
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
                        communityDetailNavigator.navigateFrom(
                            activity = activity,
                            intentBuilder = {
                                putExtra(Extras.POST_DATA, post)
                            },
                        )
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
        communityDetailNavigator = object : CommunityDetailNavigator {
            override fun navigateFrom(activity: Activity, intentBuilder: Intent.() -> Intent, withFinish: Boolean) {
                activity.startActivityWithAnimation<CommunityDetailActivity>(intentBuilder, withFinish)
            }
        },
        state = CommunityMainState(
            storyUsers = userMockData,
            posts = postMockData
        )
    )
}