package com.wepli.devmode.network.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.devmode.network.main.screen.NetworkLogDebugScreenRoute
import com.wepli.devmode.network.navigation.NetworkLogRoute

fun NavController.navigateToNetworkLogMain() {
    navigate(NetworkLogRoute.Main.route)
}

fun NavGraphBuilder.networkLogMainGraph(
    navOnNetworkLogDetail: (Int) -> Unit,
    navOnBack: () -> Unit
) {
    composable(
        route = NetworkLogRoute.Main.route
    ) {
        NetworkLogDebugScreenRoute(navOnNetworkLogDetail = navOnNetworkLogDetail, navOnBack = navOnBack)
    }
}