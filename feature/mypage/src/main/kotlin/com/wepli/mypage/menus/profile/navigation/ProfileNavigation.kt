package com.wepli.mypage.menus.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.mypage.menus.profile.screen.ProfileScreenRoute
import com.wepli.navigator.feature.mypage.ProfileRoute

fun NavController.navigateProfileMain() {
    navigate(ProfileRoute.MAIN.route)
}

fun NavGraphBuilder.profileMainGraph() {
    composable(
        route = ProfileRoute.MAIN.route
    ) {
        ProfileScreenRoute()
    }
}