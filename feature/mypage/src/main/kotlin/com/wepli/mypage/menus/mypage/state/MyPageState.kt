package com.wepli.mypage.menus.mypage.state

import com.wepli.shared.feature.uimodel.user.UserUiData

data class MyPageState(
    val user: UserUiData = UserUiData()
)