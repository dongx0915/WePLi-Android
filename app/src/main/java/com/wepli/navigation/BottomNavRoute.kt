package com.wepli.navigation

import androidx.annotation.DrawableRes
import com.wepli.designsystem.R
import com.wepli.navigator.feature.community.CommunityRoute
import com.wepli.navigator.feature.home.HomeRoute
import com.wepli.navigator.feature.mypage.MyPageRoute

sealed class BottomNavRoute(
    val route: String,
    val title: String,
    @DrawableRes val bottomTabIcon: Int? = null,
    @DrawableRes val bottomTabSelectedIcon: Int? = null
) {
    data object Home : BottomNavRoute(HomeRoute.Home.route, "홈", R.drawable.ic_home, R.drawable.ic_home_color)
    data object Search : BottomNavRoute("search", "검색", R.drawable.ic_search_2, R.drawable.ic_search_2_color)
    data object Chart : BottomNavRoute("chart", "차트", R.drawable.ic_stars, R.drawable.ic_stars_color)
    data object Community : BottomNavRoute(CommunityRoute.Home.route, "커뮤니티", R.drawable.ic_message, R.drawable.ic_message_color)
    data object MyPage : BottomNavRoute(MyPageRoute.Main.route, "내정보", R.drawable.ic_profile, R.drawable.ic_profile_color)
}