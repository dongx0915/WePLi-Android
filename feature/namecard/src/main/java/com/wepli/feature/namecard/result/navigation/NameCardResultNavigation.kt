package com.wepli.feature.namecard.result.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.feature.namecard.result.NameCardResultScreenRoute
import com.wepli.navigator.feature.namecard.NameCardRoute

// Controller
fun NavController.navigateToNameCardResult() {
    navigate(NameCardRoute.RESULT.route) {
        // Main 화면 이후 스택을 모두 제거하고 이동
        popUpTo(NameCardRoute.MAIN.route) { inclusive = true }
    }
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