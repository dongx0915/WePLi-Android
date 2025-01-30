package com.wepli.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.navigator.extras.Extras
import com.wepli.navigator.feature.search.SearchRoute
import com.wepli.search.main.screen.SearchMainScreenRoute
import com.wepli.search.detail.screen.SearchScreenRoute
import com.wepli.uimodel.music.SongUiData
import extensions.enterAnimation

// Controller - 화면 이동을 담당
fun NavController.navigateToSearchDetail(screenMode: SearchScreenMode, searchQuery: String) {
    navigate("${SearchRoute.DETAIL.route}/$screenMode/$searchQuery")
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
    navigateBackWithSelectedSongs: (List<SongUiData>) -> Unit
) {
    composable(
        route = "${SearchRoute.DETAIL.route}/{screenMode}/{searchQuery}",
        enterTransition = { enterAnimation() }
    ) {
        val screenMode = SearchScreenMode.fromString(
            it.arguments?.getString("screenMode").orEmpty()
        )
        val searchQuery = it.arguments?.getString("searchQuery").orEmpty()

        SearchScreenRoute(screenMode, searchQuery, navOnBack, navigateBackWithSelectedSongs)
    }
}