package com.wepli.devmode.network.presentation.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wepli.devmode.network.R
import com.wepli.devmode.network.theme.NetworkLogTheme

@Preview
@Composable
fun NetworkLoggerAppBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(60.dp)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = NetworkLogTheme.color.gray000,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_wifi),
                tint = NetworkLogTheme.color.gray700,
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = "Network Logger",
                style = NetworkLogTheme.typo.title3,
                color = NetworkLogTheme.color.gray900,
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "12 requests captured",
                style = NetworkLogTheme.typo.caption1,
                color = NetworkLogTheme.color.gray700,
            )
        }
    }
}
