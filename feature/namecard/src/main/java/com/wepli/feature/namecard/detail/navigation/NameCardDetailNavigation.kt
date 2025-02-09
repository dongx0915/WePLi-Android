package com.wepli.feature.namecard.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.feature.namecard.detail.NameCardDetailScreenRoute
import com.wepli.navigator.feature.namecard.NameCardRoute
import extensions.enterAnimation

// Controller
fun NavController.navigateToNameCardDetail() {
    navigate(NameCardRoute.DETAIL.route)
}

// Graph
fun NavGraphBuilder.nameCardDetailGraph(
    navOnBack: () -> Unit,
    navOnSongSearchScreen: () -> Unit,
) {
    composable(
        route = NameCardRoute.DETAIL.route,
        enterTransition = { enterAnimation() }
    ) {
        NameCardDetailScreenRoute(navOnBack, navOnSongSearchScreen)
    }
}