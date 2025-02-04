package com.wepli.home.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.home.screen.HomeRoute
import com.wepli.navigator.feature.home.HomeRoute
import com.wepli.shared.feature.common.LocalAnimatedContentScope
import com.wepli.shared.feature.common.LocalSharedTransitionScope

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.homeGraph(
    navOnPlaylistDetail: (playlistId: Int) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
) {
    composable(HomeRoute.Home.route) {
        val animatedContentScope: AnimatedContentScope = this@composable

        CompositionLocalProvider(
            LocalSharedTransitionScope provides sharedTransitionScope,
            LocalAnimatedContentScope provides animatedContentScope,
        ) {
            HomeRoute(
                onNavigatePlaylist = { playlist -> navOnPlaylistDetail(playlist) },
            )
        }
    }
}