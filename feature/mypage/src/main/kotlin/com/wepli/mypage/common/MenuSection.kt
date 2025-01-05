package com.wepli.mypage.common

import base.Intent

// MenuSection 모델 정의
data class MenuSection(
    val title: String,
    val items: List<MenuItem>
) {
    data class MenuItem(
        val title: String,
        val intent: Intent,
    )
}