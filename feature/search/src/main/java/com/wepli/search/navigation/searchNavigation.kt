package com.wepli.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.navigator.feature.search.SearchRoute
import com.wepli.search.main.screen.SearchMainScreenRoute
import com.wepli.search.detail.screen.SearchScreenRoute
import extensions.enterAnimation

// Controller - 화면 이동을 담당
fun NavController.navigateToSearchDetail(screenMode: SearchScreenMode, searchQuery: String) {
    navigate("${SearchRoute.DETAIL.route}/$screenMode/$searchQuery")
}

fun NavGraphBuilder.searchMainGraph(
    navOnSearchDetail: (searchQuery: String) -> Unit
) {
    composable(SearchRoute.MAIN.route) {
        SearchMainScreenRoute(navOnSearchDetail)
    }
}

fun NavGraphBuilder.searchDetailGraph(
    navOnBack: () -> Unit
) {
    composable(
        route = "${SearchRoute.DETAIL.route}/{screenMode}/{searchQuery}",
        enterTransition = { enterAnimation() }
    ) {
        val screenMode = SearchScreenMode.fromString(
            it.arguments?.getString("screenMode").orEmpty()
        )
        val searchQuery = it.arguments?.getString("searchQuery").orEmpty()

        SearchScreenRoute(screenMode, searchQuery, navOnBack)
    }
}