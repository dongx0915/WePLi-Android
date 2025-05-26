package com.wepli.mypage.menus.mypage.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wepli.mypage.menus.mypage.screen.MyPageScreenRoute
import com.wepli.navigator.feature.mypage.MyPageRoute


fun NavGraphBuilder.mypageMainGraph(
    navOnAppInfo: () -> Unit,
    navOnPhotoCard: () -> Unit,
    navOnDevMode: () -> Unit,
    goToLoginActivity: () -> Unit,
) {
    composable(
        route = MyPageRoute.Main.route
    ) {
        MyPageScreenRoute(
            navOnAppInfo = navOnAppInfo,
            navOnPhotoCard = navOnPhotoCard,
            navOnDevMode = navOnDevMode,
            goToLoginActivity = { goToLoginActivity() }
        )
    }
}