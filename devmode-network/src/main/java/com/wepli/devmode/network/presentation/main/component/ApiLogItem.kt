package com.wepli.devmode.network.presentation.main.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wepli.devmode.network.data.model.ApiLog
import com.wepli.devmode.network.mock.mockApiLogs
import com.wepli.devmode.network.theme.NetworkLogTheme

@Preview
@Composable
fun ApiLogItem(
    apiLog: ApiLog = mockApiLogs.first(),
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp),
    ) {
        StatusTag(status = apiLog.response.code, modifier = Modifier.padding(top = 8.dp))

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = apiLog.meta.method.name.uppercase(),
                    style = NetworkLogTheme.typo.subTitle5,
                    color = NetworkLogTheme.color.gray900,
                )

                Text(
                    text = apiLog.meta.baseUrl,
                    style = NetworkLogTheme.typo.body6,
                    color = NetworkLogTheme.color.gray500,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = apiLog.meta.decodedUrl,
                style = NetworkLogTheme.typo.body1,
                color = NetworkLogTheme.color.gray800,
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "${apiLog.meta.durationMs}ms",
                    style = NetworkLogTheme.typo.subTitle6,
                    color = NetworkLogTheme.color.gray400,
                )

                Text(
                    text = "•",
                    style = NetworkLogTheme.typo.subTitle6,
                    color = NetworkLogTheme.color.gray400,
                )

                Text(
                    text = apiLog.response.formattedBodySize,
                    style = NetworkLogTheme.typo.subTitle6,
                    color = NetworkLogTheme.color.gray400,
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = apiLog.meta.formattedStartTime(),
                    style = NetworkLogTheme.typo.subTitle6,
                    color = NetworkLogTheme.color.gray400,
                )
            }
        }
    }
}