package com.wepli.mypage.menus.devmode.network.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.mypage.menus.devmode.network.main.screen.NetworkLogDebugScreenRoute
import com.wepli.navigator.feature.mypage.NetworkLogRoute

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