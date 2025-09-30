package com.wepli.devmode.fcm.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.devmode.fcm.presentation.DevFcmPushScreenRoute

enum class DevModeFcmRoute(val route: String) {
    Main("devmode_fcm_main")
}

fun NavController.navigateToDevModeFcmMain() {
    navigate(DevModeFcmRoute.Main.route)
}

fun NavGraphBuilder.devModeFcmGraph() {
    composable(
        route = DevModeFcmRoute.Main.route
    ) {
        DevFcmPushScreenRoute()
    }
}