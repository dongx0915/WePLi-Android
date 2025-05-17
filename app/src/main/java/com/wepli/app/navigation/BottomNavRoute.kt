package com.wepli.app.navigation

import androidx.annotation.DrawableRes
import com.wepli.core.resources.R as CoreR
import com.wepli.navigator.feature.community.CommunityRoute
import com.wepli.navigator.feature.home.HomeRoute
import com.wepli.navigator.feature.mypage.MyPageRoute
import com.wepli.navigator.feature.search.SearchRoute

sealed class BottomNavRoute(
    val route: String,
    val title: String,
    @DrawableRes val bottomTabIcon: Int? = null,
    @DrawableRes val bottomTabSelectedIcon: Int? = null
) {
    data object Home : BottomNavRoute(HomeRoute.Home.route, "HOME", CoreR.drawable.ic_home, CoreR.drawable.ic_home_color)
    data object Search : BottomNavRoute(SearchRoute.MAIN.route, "SEARCH", CoreR.drawable.ic_search_2, CoreR.drawable.ic_search_2_color)
    data object Chart : BottomNavRoute("chart", "CHART", CoreR.drawable.ic_stars, CoreR.drawable.ic_stars_color)
    data object Community : BottomNavRoute(CommunityRoute.Home.route, "COMM", CoreR.drawable.ic_message, CoreR.drawable.ic_message_color)
    data object MyPage : BottomNavRoute(MyPageRoute.Main.route, "MY", CoreR.drawable.ic_profile, CoreR.drawable.ic_profile_color)
}