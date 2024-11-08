package com.wepli.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wepli.ChartScreen
import com.wepli.CommunityScreen
import com.wepli.HomeScreen
import com.wepli.MyPageScreen
import com.wepli.SearchScreen

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    startDestination: String,
) {

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
        composable(Screen.Search.route) {
            SearchScreen()
        }
        composable(Screen.Chart.route) {
            ChartScreen()
        }
        composable(Screen.Community.route) {
            CommunityScreen()
        }
        composable(Screen.MyPage.route) {
            MyPageScreen()
        }
    }
}