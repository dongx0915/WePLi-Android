package com.wepli.feature.devmode.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.feature.devmode.main.screen.DevModeScreenRoute

enum class DevModeRoute(val route: String) {
    Main("devmode_main"),
}

fun NavController.navigateToDevModeMain() {
    navigate(DevModeRoute.Main.route)
}

fun NavGraphBuilder.devModeMainGraph(
    navOnBack: () -> Unit,
    navOnNetworkLog: () -> Unit,
    navOnSendFcmPush: () -> Unit,
) {
    composable(
        route = DevModeRoute.Main.route
    ) {
        DevModeScreenRoute(navOnBack, navOnNetworkLog, navOnSendFcmPush)
    }
}