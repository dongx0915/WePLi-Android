package com.wepli.feature.namecard.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.feature.namecard.main.NameCardScreenRoute
import com.wepli.navigator.feature.namecard.NameCardRoute
import extensions.enterAnimation

// Controller
fun NavController.navigateToNameCardMain() {
    navigate(NameCardRoute.MAIN.route)
}

// Graph
fun NavGraphBuilder.nameCardMainGraph(
    navOnBack: () -> Unit,
    navOnNameCardDetail: () -> Unit,
) {
    composable(
        route = NameCardRoute.MAIN.route,
        enterTransition = { enterAnimation() }
    ) {
        NameCardScreenRoute(
            navOnBack = { navOnBack() },
            navOnNameCardDetail = { navOnNameCardDetail() }
        )
    }
}