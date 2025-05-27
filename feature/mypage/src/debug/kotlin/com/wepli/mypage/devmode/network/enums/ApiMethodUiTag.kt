package com.wepli.mypage.devmode.network.enums

import androidx.compose.ui.graphics.Color
import debug.model.ApiMethod

enum class ApiMethodUiTag {
    ALL,
    GET,
    POST,
    PUT,
    DELETE;
}

fun ApiMethod.toColor(): Color {
    return when (this) {
        ApiMethod.GET -> Color(0xFF8CAD51)
        ApiMethod.POST -> Color(0xFF7B94CB)
        ApiMethod.PUT -> Color(0xFFBB9F3A)
        ApiMethod.DELETE -> Color(0xFFE07B67)
        ApiMethod.UNKNOWN -> Color.White
    }
}