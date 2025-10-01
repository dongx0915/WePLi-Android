package com.wepli.devmode.fcm.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.wepli.devmode.fcm.presentation.DevFcmPushScreenRoute
import kotlinx.serialization.Serializable

@Serializable
data class DevModeFcmRoute(
    val token: String
)

fun NavController.navigateToDevModeFcmMain(fcmToken: String) {
    navigate(route = DevModeFcmRoute(fcmToken))
}

fun NavGraphBuilder.devModeFcmGraph(navOnBack: () -> Unit) {
    composable<DevModeFcmRoute> { entry ->
        val token = entry.toRoute<DevModeFcmRoute>().token

        DevFcmPushScreenRoute(fcmToken = token, navOnBack = navOnBack)
    }
}