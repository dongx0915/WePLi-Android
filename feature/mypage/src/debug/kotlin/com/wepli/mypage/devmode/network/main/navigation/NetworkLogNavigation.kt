package com.wepli.mypage.devmode.network.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.mypage.devmode.network.main.screen.NetworkLogDebugScreenRoute
import com.wepli.navigator.feature.mypage.NetworkLogRoute

fun NavController.navigateToNetworkLogMain() {
    navigate(NetworkLogRoute.Main.route)
}

fun NavGraphBuilder.networkLogMainGraph(
    navOnBack: () -> Unit
) {
    composable(
        route = NetworkLogRoute.Main.route
    ) {
        NetworkLogDebugScreenRoute(navOnBack = navOnBack)
    }
}