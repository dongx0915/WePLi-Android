package com.wepli.mypage.menus.appinfo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.mypage.menus.appinfo.screen.AppInfoScreen
import com.wepli.navigator.feature.mypage.MyPageRoute
import extensions.enterAnimation

fun NavController.navigateToAppInfo() {
    navigate(MyPageRoute.AppInfo.route)
}

fun NavGraphBuilder.mypageAppInfoGraph(
    navOnBack: () -> Unit
) {
    composable(
        route = MyPageRoute.AppInfo.route,
        enterTransition = { enterAnimation() }
    ) {
        AppInfoScreen(
            navOnBack = { navOnBack() }
        )
    }
}