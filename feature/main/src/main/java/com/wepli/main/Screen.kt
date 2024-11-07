package com.wepli.main

import androidx.annotation.DrawableRes
import com.wepli.designsystem.R

sealed class Screen(
    val route: String,
    val title: String,
    @DrawableRes val icon: Int,
    @DrawableRes val selectedIcon: Int
) {
    data object Home : Screen("home", "홈", R.drawable.ic_home, R.drawable.ic_home_color)
    data object Search : Screen("search", "검색", R.drawable.ic_search_2, R.drawable.ic_search_2_color)
    data object Chart : Screen("chart", "차트", R.drawable.ic_stars, R.drawable.ic_stars_color)
    data object Community : Screen("community", "커뮤니티", R.drawable.ic_message, R.drawable.ic_message_color)
    data object MyPage : Screen("mypage", "마이페이지", R.drawable.ic_profile, R.drawable.ic_profile_color)
}