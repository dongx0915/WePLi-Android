package com.wepli.feature.song.info.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wepli.feature.song.info.SongInfoScreenRoute
import com.wepli.navigator.feature.song.SongRoute
import com.wepli.uimodel.music.SongUiData
import extensions.parseFromJson
import extensions.toJsonString


fun NavController.navigateToSongInfo(song: SongUiData) {
    navigate("${SongRoute.INFO.route}/${Uri.encode(song.toJsonString())}")
}

fun NavGraphBuilder.songInfoGraph(
    navOnBack: () -> Unit
) {
    composable(
        route = "${SongRoute.INFO.route}/{song}",
        arguments = listOf(
            navArgument("song") { type = NavType.StringType }
        )
    ) {
        val song = it.arguments?.getString("song")?.parseFromJson<SongUiData>()

        song?.let { song ->
            SongInfoScreenRoute(
                song = song,
                navOnBack = navOnBack
            )
        }
    }
}