package com.wepli.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wepli.ChartScreen
import com.wepli.SearchScreen
import com.wepli.community.navigation.communityDetailGraph
import com.wepli.community.navigation.communityMainGraph
import com.wepli.community.navigation.navigateToCommunityDetail
import com.wepli.home.screen.HomeScreen
import com.wepli.mypage.menus.appinfo.navigation.mypageAppInfoGraph
import com.wepli.mypage.menus.appinfo.navigation.navigateToAppInfo
import com.wepli.mypage.menus.mypage.navigation.mypageMainGraph
import com.wepli.playlist.navigation.navigateToPlaylistDetail
import com.wepli.playlist.navigation.playlistDetailGraph

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(BottomNavRoute.Home.route) {
            HomeScreen(
                onNavigatePlaylist = { playlist -> navController.navigateToPlaylistDetail(/* playlist */) },
            )
        }
        composable(BottomNavRoute.Search.route) {
            SearchScreen()
        }
        composable(BottomNavRoute.Chart.route) {
            ChartScreen()
        }

        // 커뮤니티 Graph
        communityMainGraph(
            navOnCommunityDetail = { post -> navController.navigateToCommunityDetail(post) }
        )
        communityDetailGraph(
            navOnBack = { navController.navigateToBack() }
        )

        // 플레이리스트 Graph
        playlistDetailGraph(
            navOnBack = { navController.navigateToBack() }
        )

        // 마이페이지 Graph
        mypageMainGraph(
            navOnAppInfo = { navController.navigateToAppInfo() }
        )
        mypageAppInfoGraph(
            navOnBack = { navController.navigateToBack() }
        )
    }
}

fun NavController.navigateToBack() = navigateUp()