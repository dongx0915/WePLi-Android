package com.wepli.playlist.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wepli.navigator.feature.playlist.PlaylistRoute
import com.wepli.playlist.PlaylistScreenRoute
import com.wepli.shared.feature.common.LocalAnimatedContentScope
import com.wepli.shared.feature.common.LocalSharedTransitionScope

// Controller
fun NavController.navigateToPlaylistDetail(playlistId: Int) {
    navigate("${PlaylistRoute.Detail.route}/${playlistId}")
}

// Graph
@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.playlistDetailGraph(
    navOnBack: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
) {
    composable(
        route = "${PlaylistRoute.Detail.route}/{playlistId}",
        arguments = listOf(navArgument("playlistId") { type = NavType.IntType }),
        enterTransition = {
            fadeIn(animationSpec = tween(2000))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(500))
        },
    ) {
        CompositionLocalProvider(
            LocalSharedTransitionScope provides sharedTransitionScope,
            LocalAnimatedContentScope provides this@composable,
        ) {
            val playlistId: Int = it.arguments?.getInt("playlistId") ?: -1

            PlaylistScreenRoute(
                playlistId = playlistId,
                navOnBack = { navOnBack() },
            )
        }
    }
}