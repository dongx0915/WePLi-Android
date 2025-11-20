package com.wepli.devmode.network.main.enums

import androidx.compose.ui.graphics.Color
import com.wepli.domain.devmode.apilog.model.ApiMethod

enum class ApiMethodUiTag {
    ALL,
    GET,
    POST,
    PUT,
    PATCH,
    DELETE;
}

fun ApiMethod.toColor(): Color {
    return when (this) {
        ApiMethod.GET -> Color(0xFF8CAD51)
        ApiMethod.POST -> Color(0xFF7B94CB)
        ApiMethod.PUT,
        ApiMethod.PATCH -> Color(0xFFBB9F3A)
        ApiMethod.DELETE -> Color(0xFFE07B67)
        ApiMethod.UNKNOWN -> Color.White
    }
}