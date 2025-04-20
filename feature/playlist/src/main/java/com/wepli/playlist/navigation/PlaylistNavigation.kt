package com.wepli.playlist.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wepli.navigator.feature.playlist.PlaylistRoute
import com.wepli.playlist.PlaylistScreenRoute

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
        enterTransition = { fadeIn(tween(1000)) }
    ) {
        val playlistId: Int = it.arguments?.getInt("playlistId") ?: -1

        PlaylistScreenRoute(
            playlistId = playlistId,
            navOnBack = { navOnBack() }
        )
    }
}