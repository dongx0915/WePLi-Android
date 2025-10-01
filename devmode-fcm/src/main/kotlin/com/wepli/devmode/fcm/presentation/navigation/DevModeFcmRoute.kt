package com.wepli.devmode.fcm.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.wepli.devmode.fcm.presentation.DevFcmPushScreenRoute
import kotlinx.serialization.Serializable

@Serializable
data class DevModeFcmRoute(
    val firebaseProjectId: String
)

fun NavController.navigateToDevModeFcmMain(firebaseProjectId: String) {
    navigate(route = DevModeFcmRoute(firebaseProjectId))
}

fun NavGraphBuilder.devModeFcmGraph(navOnBack: () -> Unit) {
    composable<DevModeFcmRoute> { entry ->
        val projectId = entry.toRoute<DevModeFcmRoute>().firebaseProjectId

        DevFcmPushScreenRoute(projectId = projectId, navOnBack = navOnBack)
    }
}