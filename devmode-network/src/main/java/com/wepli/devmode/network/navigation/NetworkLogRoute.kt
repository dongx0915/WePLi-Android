package com.wepli.devmode.network.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import animation.transition.ScreenTransitions
import com.wepli.devmode.network.presentation.detail.navigation.networkLogDetailGraph
import com.wepli.devmode.network.presentation.main.navigation.networkLogMainGraph
import kotlinx.serialization.Serializable

sealed class NetworkLogRoute {
    @Serializable
    data object Main : NetworkLogRoute()

    @Serializable
    data class Detail(val logId: Int) : NetworkLogRoute()
}

@Composable
fun NetworkLogNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NetworkLogRoute.Main,
        enterTransition = { ScreenTransitions.defaultEnterTransition() },
        exitTransition = { ScreenTransitions.defaultExitTransition() },
    ) {
        networkLogMainGraph(
            navOnNetworkLogDetail = { apiLogId ->
                navController.navigate(NetworkLogRoute.Detail(apiLogId))
            },
        )

        networkLogDetailGraph(
            navOnBack = { navController.navigateUp() }
        )
    }
}