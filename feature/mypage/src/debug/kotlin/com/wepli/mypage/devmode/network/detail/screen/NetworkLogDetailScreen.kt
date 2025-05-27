package com.wepli.mypage.devmode.network.detail.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.WepliAppBar
import com.wepli.mypage.devmode.mock.mockApiLogs
import com.wepli.mypage.devmode.network.detail.viewmodel.NetworkLogDetailState
import com.wepli.mypage.devmode.network.detail.viewmodel.NetworkLogDetailViewModel
import debug.model.ApiLog
import org.orbitmvi.orbit.compose.collectAsState
import theme.WepliTheme

@Preview
@Composable
fun NetworkLogDetailScreenPreview() {
    NetworkLogDetailScreen(
        apiLog = mockApiLogs.random()
    ) {

    }
}


@Composable
fun NetworkLogDetailScreenRoute(navOnBack: () -> Unit) {
    val viewModel: NetworkLogDetailViewModel = hiltViewModel()
    val state: NetworkLogDetailState by viewModel.collectAsState()

    state.apiLog?.let {
        NetworkLogDetailScreen(
            apiLog = it,
            navOnBack = navOnBack
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetworkLogDetailScreen(
    apiLog: ApiLog,
    navOnBack: () -> Unit
) {
    Scaffold(
        containerColor = WepliTheme.color.black,
        topBar = {
            WepliAppBar(
                containerColor = Color.Transparent,
                title = "${apiLog.method.name} ${apiLog.url}",
                showBackButton = true,
                onClickBack = navOnBack
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

        }
    }
}