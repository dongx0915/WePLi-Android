package com.wepli.feature.namecard.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.feature.namecard.detail.NameCardDetailScreenRoute
import com.wepli.navigator.extras.Extras
import com.wepli.navigator.feature.namecard.NameCardRoute
import com.wepli.shared.feature.uimodel.namecard.PhotoCardUiData
import com.wepli.uimodel.music.SongUiData
import extensions.enterAnimation

// Controller
fun NavController.navigateToNameCardDetail() {
    navigate(NameCardRoute.DETAIL.route)
}

// Graph
fun NavGraphBuilder.nameCardDetailGraph(
    navOnBack: () -> Unit,
    navOnSongSearchScreen: () -> Unit,
    navOnNameCardResultScreen: (nameCardInfo: PhotoCardUiData)-> Unit,
) {
    composable(
        route = NameCardRoute.DETAIL.route,
        enterTransition = { enterAnimation() }
    ) {
        val selectedSong: SongUiData? = it.savedStateHandle.remove<List<SongUiData>>(Extras.SELECTED_SONGS)?.first()

        NameCardDetailScreenRoute(selectedSong, navOnBack, navOnSongSearchScreen, navOnNameCardResultScreen)
    }
}