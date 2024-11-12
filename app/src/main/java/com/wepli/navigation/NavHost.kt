package com.wepli.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wepli.ChartScreen
import com.wepli.SearchScreen
import com.wepli.community.navigation.communityDetailGraph
import com.wepli.community.navigation.communityMainGraph
import com.wepli.community.navigation.navigateToCommunityDetail
import com.wepli.home.navigation.homeGraph
import com.wepli.home.screen.HomeRoute
import com.wepli.home.screen.HomeScreen
import com.wepli.mypage.menus.appinfo.navigation.mypageAppInfoGraph
import com.wepli.mypage.menus.appinfo.navigation.navigateToAppInfo
import com.wepli.mypage.menus.mypage.navigation.mypageMainGraph
import com.wepli.navigation.extensions.navigateToBack
import com.wepli.playlist.navigation.navigateToPlaylistDetail
import com.wepli.playlist.navigation.playlistDetailGraph

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            fadeIn(animationSpec = tween(500))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(500))
        }
    ) {
        // 홈 Graph
        homeGraph(
            navOnPlaylistDetail = { playlist -> navController.navigateToPlaylistDetail(/* playlist */) }
        )

        composable(BottomNavRoute.Search.route) {
            SearchScreen()
        }
        composable(BottomNavRoute.Chart.route) {
            ChartScreen()
        }

        // 커뮤니티 Graph
        communityGraph(navController)

        // 플레이리스트 Graph
        playlistGraph(navController)

        // 마이페이지 Graph
        mypageGraph(navController)
    }
}

// 커뮤니티 Graph
fun NavGraphBuilder.communityGraph(navController: NavHostController) {
    communityMainGraph(
        navOnCommunityDetail = { post -> navController.navigateToCommunityDetail(post) }
    )
    communityDetailGraph(
        navOnBack = { navController.navigateToBack() }
    )
}

// 플레이리스트 Graph
fun NavGraphBuilder.playlistGraph(navController: NavHostController) {
    playlistDetailGraph(
        navOnBack = { navController.navigateToBack() }
    )
}

// 마이페이지 Graph
fun NavGraphBuilder.mypageGraph(navController: NavHostController) {
    mypageMainGraph(
        navOnAppInfo = { navController.navigateToAppInfo() }
    )
    mypageAppInfoGraph(
        navOnBack = { navController.navigateToBack() }
    )
}