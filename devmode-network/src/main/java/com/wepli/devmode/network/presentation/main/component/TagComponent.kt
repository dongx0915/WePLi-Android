package com.wepli.devmode.network.presentation.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wepli.devmode.network.theme.NetworkLogTheme

@Preview(showBackground = true)
@Composable
fun PreviewMethodTag() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(16.dp)) {
        MethodTag(tagName = "GET", isSelected = true)
        MethodTag(tagName = "POST", isSelected = false)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewStatusTag() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(16.dp)) {
        StatusTag(status = 100)
        StatusTag(status = 200)
        StatusTag(status = 301)
        StatusTag(status = 404)
        StatusTag(status = 500)
        StatusTag(status = 999)
    }
}

@Composable
fun MethodTag(tagName: String, isSelected: Boolean, modifier: Modifier = Modifier) {
    val textColor = if (isSelected) NetworkLogTheme.color.gray900 else NetworkLogTheme.color.gray200
    val backgroundColor = if (isSelected) NetworkLogTheme.color.gray000 else Color.Transparent

    Box(
        modifier = modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
            .border(width = 1.dp, color = NetworkLogTheme.color.gray050, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = tagName,
            style = NetworkLogTheme.typo.subTitle5,
            color = textColor
        )
    }
}

@Composable
fun StatusTag(status: Int) {
    val (statusColor, backgroundColor) = remember(status) {
        when(status) {
            in 100..199 -> Color(0xFF51A2FF) to Color(0x1A51A2FF) // 1xx: Informational
            in 200..299 -> Color(0xFF00D492) to Color(0x1A00BC7D) // 2xx: Success
            in 300..399 -> Color(0xFFFFB900) to Color(0x1AFE9A00) // 3xx: Redirection
            in 400..499 -> Color(0xFFFF6467) to Color(0x1AFB2C36) // 4xx: Client Error
            in 500..599 -> Color(0xFFFF6467) to Color(0x1AFB2C36) // 5xx: Server Error
            else -> Color(0xFFF0F0F0) to Color(0x1A818181) // Unknown status
        }
    }

    Text(
        text = status.toString(),
        style = NetworkLogTheme.typo.subTitle6,
        color = statusColor,
        modifier = Modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}

