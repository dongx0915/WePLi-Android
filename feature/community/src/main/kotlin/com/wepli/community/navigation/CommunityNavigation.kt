package com.wepli.community.navigation

import android.net.Uri
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wepli.community.detail.CommunityDetailScreen
import com.wepli.community.detail.CommunityDetailViewModel
import com.wepli.community.main.CommunityScreen
import com.wepli.navigator.feature.community.CommunityRoute
import com.wepli.shared.feature.uimodel.community.PostUiData
import extensions.enterAnimation
import extensions.parseFromJson
import extensions.toJsonString

// Controller - 화면 이동을 담당
fun NavController.navigateToCommunityDetail(post: PostUiData) {
    navigate("${CommunityRoute.Detail.route}/${Uri.encode(post.toJsonString())}")
}

// Graph - 도착 지점(화면)을 정의
fun NavGraphBuilder.communityMainGraph(
    navOnCommunityDetail: (PostUiData) -> Unit
) {
    composable(CommunityRoute.Home.route) {
        CommunityScreen(
            navOnCommunityDetail = { post -> navOnCommunityDetail(post) }
        )
    }
}

fun NavGraphBuilder.communityDetailGraph(
    navOnBack: () -> Unit
) {
    composable(
        route = "${CommunityRoute.Detail.route}/{post}",
        arguments = listOf(
            navArgument("post") { type = NavType.StringType }
        ),
        enterTransition = { enterAnimation() }
    ) {
        // TODO 해당 부분을 CommunityDetailScreenRoute()에서 수행하고, Graph 부분은 Core 모듈로 이동시켜도 될 것 같음
        val post = it.arguments?.getString("post")?.parseFromJson<PostUiData>()
        val viewModel = hiltViewModel<CommunityDetailViewModel>()

        LaunchedEffect(Unit) {
            viewModel.setPost(post ?: PostUiData())
        }

        CommunityDetailScreen(viewModel = viewModel, navOnBack = { navOnBack() })
    }
}