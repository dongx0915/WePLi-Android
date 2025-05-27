package com.wepli.mypage.devmode.network.detail.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import animation.transition.ScreenTransitions
import com.wepli.mypage.devmode.network.detail.screen.NetworkLogDetailScreenRoute
import com.wepli.mypage.devmode.network.main.screen.NetworkLogDebugScreenRoute
import com.wepli.navigator.feature.mypage.NetworkLogRoute

fun NavController.navigateToNetworkLogDetail(apiLogId: String) {
    navigate("${NetworkLogRoute.Detail.route}/${apiLogId}")
}

fun NavGraphBuilder.networkLogDetailGraph(
    navOnBack: () -> Unit
) {
    composable(
        route = "${NetworkLogRoute.Detail.route}/{apiLogId}",
        arguments = listOf(navArgument("apiLogId") { type = NavType.StringType }),
        enterTransition = { ScreenTransitions.defaultEnterTransition() },
        exitTransition = { ScreenTransitions.defaultExitTransition() },
    ) {
        val apiLogId: String = it.arguments?.getString("apiLogId") ?: ""

        NetworkLogDetailScreenRoute(apiLogId = apiLogId, navOnBack = navOnBack)
    }
}