package com.wepli.feature.namecard.result.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.feature.namecard.result.NameCardResultScreenRoute
import com.wepli.navigator.feature.namecard.NameCardRoute

// Controller
fun NavController.navigateToNameCardResult() {
    navigate(NameCardRoute.RESULT.route)
}

// Graph
fun NavGraphBuilder.nameCardResultGraph(
    navOnBack: () -> Unit,
) {
    composable(
        route = NameCardRoute.RESULT.route,
    ) {
        NameCardResultScreenRoute(navOnBack)
    }
}