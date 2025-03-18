package com.wepli.feature.song.info.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.feature.song.info.SongInfoScreenRoute
import com.wepli.navigator.feature.song.SongRoute
import com.wepli.uimodel.music.SongUiData


fun NavController.navigateToSongInfo(song: SongUiData) {
    navigate(SongRoute.INFO.route)
}

fun NavGraphBuilder.songInfoGraph(
    navOnBack: () -> Unit
) {
    composable(
        route = SongRoute.INFO.route,
    ) {
        SongInfoScreenRoute(navOnBack)
    }
}