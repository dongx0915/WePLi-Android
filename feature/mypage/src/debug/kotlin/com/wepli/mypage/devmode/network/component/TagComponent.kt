package com.wepli.mypage.devmode.network.component

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
import theme.WepliTheme

@Preview(showBackground = true)
@Composable
fun PreviewMethodTag() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(16.dp)) {
        MethodTag(tagName = "GET", isSelected = true)
        MethodTag(tagName = "POST", isSelected = false)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStatusTag() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(16.dp)) {
        StatusTag(status = 200)
        StatusTag(status = 301)
        StatusTag(status = 404)
        StatusTag(status = 500)
        StatusTag(status = 999)
    }
}

@Composable
fun MethodTag(tagName: String, isSelected: Boolean, modifier: Modifier = Modifier) {
    val textColor = if (isSelected) WepliTheme.color.gray900 else WepliTheme.color.gray200
    val backgroundColor = if (isSelected) WepliTheme.color.gray000 else Color.Transparent

    Box(
        modifier = modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
            .border(width = 1.dp, color = WepliTheme.color.gray050, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = tagName,
            style = WepliTheme.typo.subTitle5,
            color = textColor
        )
    }
}

@Composable
fun StatusTag(status: Int) {
    val (statusColor, backgroundColor) = remember(status) {
        when(status) {
            in 100..199 -> Color(0xFF4CAF50) to Color(0xFF1C2015) // 1xx: Informational
            in 200..299 -> Color(0xFF4CAF50) to Color(0xFF1C2015) // 2xx: Success
            in 300..399 -> Color(0xFFFF9800) to Color(0xFF211E12) // 3xx: Redirection
            in 400..499 -> Color(0xFFF44336) to Color(0xFF261A17) // 4xx: Client Error
            in 500..599 -> Color(0xFFF44336) to Color(0xFF261A17) // 5xx: Server Error
            else -> Color(0xFF9E9E9E) to Color(0xFF9E9E9E) // Unknown status
        }
    }

    Text(
        text = "200",
        style = WepliTheme.typo.subTitle6,
        color = statusColor,
        modifier = Modifier
            .background(color = backgroundColor, shape = CircleShape)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

