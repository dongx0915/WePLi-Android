package com.wepli.relaylist.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wepli.navigator.feature.relaylist.RelaylistRoute
import com.wepli.relaylist.detail.RelaylistDetailScreenRoute
import extensions.enterAnimation


fun NavController.navigateToRelaylistDetail(relaylistId: Int) {
    navigate("${RelaylistRoute.Detail.route}/$relaylistId")
}

fun NavGraphBuilder.relaylistDetailGraph(
    navOnBack: () -> Unit
) {
    composable(
        route = "${RelaylistRoute.Detail.route}/{relaylistId}",
        arguments = listOf(navArgument("relaylistId") { type = NavType.IntType }),
        enterTransition = { enterAnimation() }
    ) {
        val relaylistId: Int = it.arguments?.getInt("relaylistId") ?: -1

        RelaylistDetailScreenRoute(navOnBack)
    }
}