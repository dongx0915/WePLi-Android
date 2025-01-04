package com.wepli.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.navigator.feature.search.SearchRoute
import com.wepli.search.screen.SearchScreenRoute


fun NavGraphBuilder.searchGraph() {
    composable(SearchRoute.SEARCH.route) {
        SearchScreenRoute()
    }
}