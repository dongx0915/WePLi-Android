package com.wepli.playlist.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wepli.navigator.feature.playlist.PlaylistRoute
import com.wepli.playlist.PlaylistScreen
import com.wepli.playlist.PlaylistScreenRoute
import extensions.enterAnimation

// Controller
fun NavController.navigateToPlaylistDetail(playlistId: Int) {
    navigate("${PlaylistRoute.Detail.route}/${playlistId}")
}

// Graph
fun NavGraphBuilder.playlistDetailGraph(
    navOnBack: () -> Unit
) {
    composable(
        route = "${PlaylistRoute.Detail.route}/{playlistId}",
        arguments = listOf(navArgument("playlistId") { type = NavType.IntType }),
        enterTransition = { enterAnimation() }
    ) {
        val playlistId: Int = it.arguments?.getInt("playlistId") ?: -1

        PlaylistScreenRoute(
            playlistId = playlistId,
            navOnBack = { navOnBack() }
        )
    }
}