package com.wepli.playlist.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.navigator.feature.playlist.PlaylistRoute
import com.wepli.playlist.PlaylistScreen
import extensions.enterAnimation

// Controller
fun NavController.navigateToPlaylistDetail() {
    navigate(PlaylistRoute.Detail.route)
}

// Graph
fun NavGraphBuilder.playlistDetailGraph(
    navOnBack: () -> Unit
) {
    composable(
        route = PlaylistRoute.Detail.route,
        enterTransition = { enterAnimation() }
    ) {
        PlaylistScreen(
            navOnBack = { navOnBack() }
        )
    }
}