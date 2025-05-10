package com.wepli.community.navigation

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wepli.community.detail.CommunityDetailScreen
import com.wepli.community.detail.CommunityDetailScreenRoute
import com.wepli.community.detail.CommunityDetailViewModel
import com.wepli.community.main.screen.CommunityMainScreenRoute
import com.wepli.community.write.screen.CommunityWriteScreenRoute
import com.wepli.navigator.extras.Extras
import com.wepli.navigator.feature.community.CommunityRoute
import com.wepli.shared.feature.uimodel.community.PostUiData
import com.wepli.uimodel.music.SongUiData
import extensions.enterAnimation
import extensions.parseFromJson
import extensions.toJsonString

// Controller - 화면 이동을 담당
fun NavController.navigateToCommunityDetail(post: PostUiData) {
    navigate("${CommunityRoute.Detail.route}/${Uri.encode(post.toJsonString())}")
}

fun NavController.navigateToCommunityWrite() {
    navigate(CommunityRoute.Write.route)
}

fun NavController.navigateToBackAndPostRefresh() {
    previousBackStackEntry?.savedStateHandle?.set(Extras.COMMUNITY_NEED_REFRESH_POST, true)
    navigateUp()
}

// Graph - 도착 지점(화면)을 정의
fun NavGraphBuilder.communityMainGraph(
    navOnCommunityDetail: (PostUiData) -> Unit,
    navOnCommunityWrite: () -> Unit
) {
    composable(CommunityRoute.Home.route) {
        val needRefresh = it.savedStateHandle.remove<Boolean>(Extras.COMMUNITY_NEED_REFRESH_POST) ?: false

        CommunityMainScreenRoute(
            needRefresh = needRefresh,
            navOnCommunityDetail = { post -> navOnCommunityDetail(post) },
            navOnCommunityWrite = { navOnCommunityWrite() }
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
        Log.d("CommunityDetailScreen", "${it.arguments?.getString("post")?.parseFromJson<PostUiData>()}")
        // TODO 해당 부분을 CommunityDetailScreenRoute()에서 수행하고, Graph 부분은 Core 모듈로 이동시켜도 될 것 같음
        val post = it.arguments?.getString("post")?.parseFromJson<PostUiData>()

        CommunityDetailScreenRoute(post = post ?: PostUiData(), navOnBack = { navOnBack() })
    }
}

fun NavGraphBuilder.communityWriteGraph(
    navOnBack: () -> Unit,
    navOnBackAndPostRefresh: () -> Unit,
    navOnSearchDetail: () -> Unit,
) {
    composable(CommunityRoute.Write.route) {
        val selectedSongs: List<SongUiData>? = it.savedStateHandle.remove<List<SongUiData>>(Extras.SELECTED_SONGS)

        CommunityWriteScreenRoute(selectedSongs, navOnBack, navOnBackAndPostRefresh, navOnSearchDetail)
    }
}