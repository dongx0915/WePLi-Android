package com.wepli.devmode.network.presentation.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.devmode.network.navigation.NetworkLogRoute
import com.wepli.devmode.network.presentation.main.screen.NetworkLogDebugScreenRoute

fun NavGraphBuilder.networkLogMainGraph(
    navOnNetworkLogDetail: (Int) -> Unit,
    onFinishActivity: () -> Unit
) {
    composable<NetworkLogRoute.Main> {
        NetworkLogDebugScreenRoute(
            navOnNetworkLogDetail = navOnNetworkLogDetail,
            onFinishActivity = onFinishActivity
        )
    }
}