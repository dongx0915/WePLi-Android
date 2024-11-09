package com.wepli.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wepli.ChartScreen
import com.wepli.MyPageScreen
import com.wepli.SearchScreen
import com.wepli.community.main.CommunityScreen
import com.wepli.community.navigation.communityDetailGraph
import com.wepli.community.navigation.communityMainGraph
import com.wepli.community.navigation.navigateToCommunityDetail
import com.wepli.home.screen.HomeScreen

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    startDestination: String,
) {

    NavHost(navController = navController, startDestination = startDestination) {
        composable(BottomNavRoute.Home.route) {
            HomeScreen()
        }
        composable(BottomNavRoute.Search.route) {
            SearchScreen()
        }
        composable(BottomNavRoute.Chart.route) {
            ChartScreen()
        }
        composable(BottomNavRoute.MyPage.route) {
            MyPageScreen()
        }

        // 커뮤니티 Graph
        communityMainGraph(
            navOnCommunityDetail = { post -> navController.navigateToCommunityDetail(post) }
        )
        communityDetailGraph()
    }
}