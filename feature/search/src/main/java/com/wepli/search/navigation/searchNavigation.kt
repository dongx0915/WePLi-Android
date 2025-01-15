package com.wepli.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.navigator.feature.search.SearchRoute
import com.wepli.search.main.screen.SearchMainScreenRoute
import com.wepli.search.detail.SearchScreenRoute
import extensions.enterAnimation

// Controller - 화면 이동을 담당
fun NavController.navigateToSearchDetail() {
    navigate(SearchRoute.DETAIL.route)
}

// Graph - 도착 지점(화면)을 정의
fun NavGraphBuilder.searchGraph(navController: NavController) {
    searchMainGraph {
        navController.navigateToSearchDetail()
    }

    searchDetailGraph()
}

internal fun NavGraphBuilder.searchMainGraph(
    navOnSearchDetail: () -> Unit
) {
    composable(SearchRoute.MAIN.route) {
        SearchMainScreenRoute(navOnSearchDetail)
    }
}

internal fun NavGraphBuilder.searchDetailGraph() {
    composable(
        route = SearchRoute.DETAIL.route,
        enterTransition = { enterAnimation() }
    ) {
        SearchScreenRoute()
    }
}