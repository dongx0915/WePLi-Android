package com.wepli.devmode.network.presentation.detail.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.wepli.devmode.network.navigation.NetworkLogRoute
import com.wepli.devmode.network.presentation.detail.screen.NetworkLogDetailScreenRoute

fun NavGraphBuilder.networkLogDetailGraph(
    navOnBack: () -> Unit
) {
    composable<NetworkLogRoute.Detail> { backStackEntry ->
        val logDetailRoute: NetworkLogRoute.Detail = backStackEntry.toRoute()

        NetworkLogDetailScreenRoute(apiLogId = logDetailRoute.logId, navOnBack = navOnBack)
    }
}