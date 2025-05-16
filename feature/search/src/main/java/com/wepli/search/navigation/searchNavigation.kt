package com.wepli.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import animation.transition.ScreenTransitions
import com.wepli.navigator.extras.Extras
import com.wepli.navigator.feature.search.SearchRoute
import com.wepli.search.detail.screen.SearchScreenRoute
import com.wepli.search.main.screen.SearchMainScreenRoute
import com.wepli.uimodel.music.SongUiData
import extensions.enterAnimation

// Controller - 화면 이동을 담당
fun NavController.navigateToSearchDetail(screenMode: SearchScreenMode, searchQuery: String) {
    val maxCount = when (screenMode) {
        is SearchScreenMode.Selectable -> screenMode.maxSelectCount
        SearchScreenMode.Normal -> Int.MAX_VALUE
    }

    navigate("${SearchRoute.DETAIL.route}/$screenMode/$maxCount/$searchQuery")
}

fun NavController.navigateBackWithSelectedSongs(selectedSongs: List<SongUiData>) {
    previousBackStackEntry?.savedStateHandle?.set(Extras.SELECTED_SONGS, selectedSongs)
    navigateUp()
}

fun NavGraphBuilder.searchMainGraph(
    navOnSearchDetail: (searchQuery: String) -> Unit
) {
    composable(SearchRoute.MAIN.route) {
        SearchMainScreenRoute(navOnSearchDetail)
    }
}

fun NavGraphBuilder.searchDetailGraph(
    navOnBack: () -> Unit,
    navigateBackWithSelectedSongs: (List<SongUiData>) -> Unit,
    navigateSongInfo: (SongUiData) -> Unit,
) {
    composable(
        route = "${SearchRoute.DETAIL.route}/{screenMode}/{maxCount}/{searchQuery}",
        arguments = listOf(
            navArgument("maxCount") { type = NavType.IntType }
        ),
        enterTransition = { ScreenTransitions.defaultEnterTransition() },
        exitTransition = { ScreenTransitions.defaultExitTransition() },
        popEnterTransition = { null },
        popExitTransition = { null },
    ) {
        val searchQuery = it.arguments?.getString("searchQuery").orEmpty()
        val maxCount = it.arguments?.getInt("maxCount") ?: Int.MAX_VALUE
        val screenMode = SearchScreenMode.fromString(
            value = it.arguments?.getString("screenMode").orEmpty(),
            maxCount = maxCount
        )

        SearchScreenRoute(searchQuery, screenMode, navOnBack, navigateBackWithSelectedSongs,navigateSongInfo)
    }
}