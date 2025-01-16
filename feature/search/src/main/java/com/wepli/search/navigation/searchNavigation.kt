package com.wepli.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.navigator.feature.search.SearchRoute
import com.wepli.search.main.screen.SearchMainScreenRoute
import com.wepli.search.detail.screen.SearchScreenRoute
import extensions.enterAnimation

// Controller - 화면 이동을 담당
fun NavController.navigateToSearchDetail(searchQuery: String) {
    navigate("${SearchRoute.DETAIL.route}/$searchQuery")
}

// Graph - 도착 지점(화면)을 정의
fun NavGraphBuilder.searchGraph(navController: NavController) {
    searchMainGraph { searchQuery ->
        navController.navigateToSearchDetail(searchQuery)
    }

    searchDetailGraph()
}

internal fun NavGraphBuilder.searchMainGraph(
    navOnSearchDetail: (searchQuery: String) -> Unit
) {
    composable(SearchRoute.MAIN.route) {
        SearchMainScreenRoute(navOnSearchDetail)
    }
}

internal fun NavGraphBuilder.searchDetailGraph() {
    composable(
        route = "${SearchRoute.DETAIL.route}/{searchQuery}",
        enterTransition = { enterAnimation() }
    ) {
        val searchQuery = it.arguments?.getString("searchQuery").orEmpty()

        SearchScreenRoute(searchQuery)
    }
}