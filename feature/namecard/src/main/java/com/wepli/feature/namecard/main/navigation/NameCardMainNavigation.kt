package com.wepli.feature.namecard.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.feature.namecard.main.NameCardScreenRoute
import com.wepli.navigator.feature.namecard.NameCardRoute

// Controller
fun NavController.navigateToPhotoCardMain() {
    navigate(NameCardRoute.MAIN.route)
}

// Graph
fun NavGraphBuilder.photoCardMainGraph(
    navOnBack: () -> Unit,
    navOnPhotoCardDetail: () -> Unit,
) {
    composable(
        route = NameCardRoute.MAIN.route,
    ) {
        NameCardScreenRoute(
            navOnBack = { navOnBack() },
            navOnNameCardDetail = { navOnPhotoCardDetail() }
        )
    }
}