package com.wepli.feature.photocard.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.feature.photocard.main.PhotoCardScreenRoute
import com.wepli.navigator.feature.photocard.PhotoCardRoute

// Controller
fun NavController.navigateToPhotoCardMain() {
    navigate(PhotoCardRoute.MAIN.route)
}

// Graph
fun NavGraphBuilder.photoCardMainGraph(
    navOnBack: () -> Unit,
    navOnPhotoCardDetail: () -> Unit,
) {
    composable(
        route = PhotoCardRoute.MAIN.route,
    ) {
        PhotoCardScreenRoute(
            navOnBack = { navOnBack() },
            navOnPhotoCardDetail = { navOnPhotoCardDetail() }
        )
    }
}