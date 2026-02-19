package com.wepli.devmode.network.presentation.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wepli.devmode.network.presentation.main.enums.toColor
import com.wepli.devmode.network.data.model.ApiLog
import com.wepli.devmode.network.theme.NetworkLogTheme

@Composable
fun ApiResultComponent(apiLog: ApiLog, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .background(color = NetworkLogTheme.color.gray050, shape = RoundedCornerShape(8.dp))
            .border(width = 1.dp, color = NetworkLogTheme.color.gray100, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = apiLog.method.name.uppercase(),
                style = NetworkLogTheme.typo.subTitle2,
                color = apiLog.method.toColor(),
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = apiLog.baseUrlType,
                style = NetworkLogTheme.typo.body6,
                color = NetworkLogTheme.color.gray700,
            )

            Spacer(modifier = Modifier.weight(1f))

            StatusTag(apiLog.responseCode)
        }

        // Body
        Text(
            text = apiLog.decodedUrl,
            style = NetworkLogTheme.typo.subTitle2,
            color = NetworkLogTheme.color.gray900,
        )

        // Footer
        Row {
            Text(
                text = apiLog.formattedStartTime(),
                style = NetworkLogTheme.typo.body6,
                color = NetworkLogTheme.color.gray600,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = apiLog.durationMs.toString() + "ms",
                style = NetworkLogTheme.typo.body3,
                color = NetworkLogTheme.color.gray600,
            )
        }
    }
}