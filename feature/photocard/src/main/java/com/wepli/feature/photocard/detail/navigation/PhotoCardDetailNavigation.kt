package com.wepli.feature.photocard.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.feature.photocard.detail.PhotoCardDetailScreenRoute
import com.wepli.navigator.extras.Extras
import com.wepli.navigator.feature.namecard.PhotoCardRoute
import com.wepli.shared.feature.uimodel.photocard.PhotoCardUiData
import com.wepli.uimodel.music.SongUiData
import extensions.enterAnimation

// Controller
fun NavController.navigateToPhotoCardDetail() {
    navigate(PhotoCardRoute.DETAIL.route)
}

// Graph
fun NavGraphBuilder.photoCardDetailGraph(
    navOnBack: () -> Unit,
    navOnSongSearchScreen: () -> Unit,
    navOnPhotoCardResultScreen: (photoCardInfo: PhotoCardUiData)-> Unit,
) {
    composable(
        route = PhotoCardRoute.DETAIL.route,
        enterTransition = { enterAnimation() }
    ) {
        val selectedSong: SongUiData? = it.savedStateHandle.remove<List<SongUiData>>(Extras.SELECTED_SONGS)?.first()

        PhotoCardDetailScreenRoute(selectedSong, navOnBack, navOnSongSearchScreen, navOnPhotoCardResultScreen)
    }
}