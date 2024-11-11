package com.wepli.mypage.menus.mypage.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.mypage.menus.mypage.screen.MyPageScreen
import com.wepli.navigator.feature.mypage.MyPageRoute


fun NavGraphBuilder.mypageMainGraph(
    navOnAppInfo: () -> Unit
) {
    composable(
        route = MyPageRoute.Main.route
    ) {
        MyPageScreen(
            navOnAppInfo = { navOnAppInfo() }
        )
    }
}