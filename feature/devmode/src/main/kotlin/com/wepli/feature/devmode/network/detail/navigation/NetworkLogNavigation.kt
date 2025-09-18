package com.wepli.feature.devmode.network.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import animation.transition.ScreenTransitions
import com.wepli.feature.devmode.network.detail.screen.NetworkLogDetailScreenRoute
import com.wepli.feature.devmode.navigation.NetworkLogRoute

fun NavController.navigateToNetworkLogDetail(apiLogId: Int) {
    navigate("${NetworkLogRoute.Detail.route}/${apiLogId}")
}

fun NavGraphBuilder.networkLogDetailGraph(
    navOnBack: () -> Unit
) {
    composable(
        route = "${NetworkLogRoute.Detail.route}/{apiLogId}",
        arguments = listOf(navArgument("apiLogId") { type = NavType.IntType }),
        enterTransition = { ScreenTransitions.defaultEnterTransition() },
        exitTransition = { ScreenTransitions.defaultExitTransition() },
    ) {
        val apiLogId: Int = it.arguments?.getInt("apiLogId") ?: -1

        NetworkLogDetailScreenRoute(apiLogId = apiLogId, navOnBack = navOnBack)
    }
}