package com.wepli.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.home.screen.HomeRoute
import com.wepli.navigator.feature.home.HomeRoute

fun NavGraphBuilder.homeGraph(
    navOnPlaylistDetail: (playlistId: Int) -> Unit,
    navOnRelaylistDetail: (relaylistId: Int) -> Unit,
) {
    composable(HomeRoute.Home.route) {
        HomeRoute(
            onNavigatePlaylist = { playlist -> navOnPlaylistDetail(playlist) },
            onNavigateRelaylist = { relaylist -> navOnRelaylistDetail(relaylist) }
        )
    }
}