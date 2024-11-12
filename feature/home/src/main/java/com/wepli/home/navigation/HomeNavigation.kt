package com.wepli.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.home.screen.HomeRoute
import com.wepli.navigator.feature.home.HomeRoute
import model.playlist.RecommendPlaylist

fun NavGraphBuilder.homeGraph(
    navOnPlaylistDetail: (RecommendPlaylist) -> Unit
) {
    composable(HomeRoute.Home.route) {
        HomeRoute(
            onNavigatePlaylist = { playlist -> navOnPlaylistDetail(playlist) },
        )
    }
}