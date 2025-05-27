package com.wepli.mypage.devmode.network.detail.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.WepliAppBar
import com.wepli.mypage.devmode.mock.mockApiLogs
import com.wepli.mypage.devmode.network.detail.viewmodel.NetworkLogDetailIntent
import com.wepli.mypage.devmode.network.detail.viewmodel.NetworkLogDetailState
import com.wepli.mypage.devmode.network.detail.viewmodel.NetworkLogDetailViewModel
import com.wepli.mypage.devmode.network.main.component.StatusTag
import com.wepli.mypage.devmode.network.main.enums.toColor
import debug.model.ApiLog
import org.orbitmvi.orbit.compose.collectAsState
import theme.WepliTheme

@Preview
@Composable
fun NetworkLogDetailScreenPreview() {
    NetworkLogDetailScreen(
        state = NetworkLogDetailState(
            apiLog = mockApiLogs.firstOrNull()
        ),
    ) { }
}

@Composable
fun NetworkLogDetailScreenRoute(apiLogId: String, navOnBack: () -> Unit) {
    val viewModel: NetworkLogDetailViewModel = hiltViewModel()
    val state: NetworkLogDetailState by viewModel.collectAsState()

    LaunchedEffect(apiLogId) {
        viewModel.processIntent(
            NetworkLogDetailIntent.InitApiLog(apiLogId)
        )
    }

    NetworkLogDetailScreen(
        state = state,
        navOnBack = navOnBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetworkLogDetailScreen(
    state: NetworkLogDetailState,
    navOnBack: () -> Unit
) {
    state.apiLog ?: return

    Scaffold(
        containerColor = WepliTheme.color.black,
        topBar = {
            WepliAppBar(
                containerColor = Color.Transparent,
                title = "${state.apiLog?.method?.name} ${state.apiLog?.url}",
                showBackButton = true,
                onClickBack = navOnBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(vertical = 20.dp, horizontal = 20.dp)
        ) {
            ApiInfoHeader(apiLog = state.apiLog)
        }
    }
}

@Composable
private fun ApiInfoHeader(apiLog: ApiLog) {
    Column {
        // Status Tag
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = apiLog.method.name.uppercase(),
                style = WepliTheme.typo.subTitle2,
                color = apiLog.method.toColor(),
            )

            StatusTag(apiLog.responseCode)
        }

        // Url
        Text(
            text = apiLog.decodedUrl,
            style = WepliTheme.typo.subTitle5,
            color = WepliTheme.color.gray800,
            modifier = Modifier.padding(top = 16.dp)
        )

        ApiSubInfoComponent(
            title = "Time:",
            data = apiLog.formattedStartTime(),
            modifier = Modifier.padding(top = 24.dp)
        )

        ApiSubInfoComponent(
            title = "Duration:",
            data = apiLog.durationMs.toString() + "ms",
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}

@Composable
private fun ApiSubInfoComponent(title: String, data: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        Text(
            text = title,
            style = WepliTheme.typo.body3,
            color = WepliTheme.color.gray600,
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = data,
            style = WepliTheme.typo.body3,
            color = WepliTheme.color.gray800,
        )
    }
}