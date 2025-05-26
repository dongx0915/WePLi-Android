package com.wepli.mypage.devmode.network.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.WepliAppBar
import com.wepli.mypage.devmode.network.component.ApiResultComponent
import com.wepli.mypage.devmode.network.component.MethodTag
import com.wepli.mypage.devmode.network.enums.ApiMethodUiModel
import com.wepli.mypage.devmode.network.viewmodel.NetworkLogState
import com.wepli.mypage.devmode.network.viewmodel.NetworkLogViewModel
import debug.model.ApiLog
import debug.model.ApiMethod
import org.orbitmvi.orbit.compose.collectAsState
import theme.WepliTheme
import com.wepli.designsystem.R as CoreR


@Preview
@Composable
fun NetworkLogDebugScreenPreview() {
    val mockApiLogs = listOf(
        ApiLog(
            method = ApiMethod.GET,
            baseUrlType = "Production",
            baseUrl = "https://api.example.com",
            url = "/users",
            requestHeaders = "Authorization: Bearer token123",
            requestBody = "",
            responseCode = 200,
            responseBody = "[{\"id\":1,\"name\":\"Alice\"},{\"id\":2,\"name\":\"Bob\"}]",
            durationMs = 120
        ),
        ApiLog(
            method = ApiMethod.POST,
            baseUrlType = "Production",
            baseUrl = "https://api.example.com",
            url = "/login",
            requestHeaders = "Content-Type: application/json",
            requestBody = "{\"username\":\"john\",\"password\":\"secret\"}",
            responseCode = 401,
            responseBody = "{\"error\":\"Invalid credentials\"}",
            durationMs = 98
        ),
        ApiLog(
            method = ApiMethod.PUT,
            baseUrlType = "Production",
            baseUrl = "https://api.example.com",
            url = "/users/1",
            requestHeaders = "Authorization: Bearer token123\nContent-Type: application/json",
            requestBody = "{\"name\":\"Alice Updated\"}",
            responseCode = 200,
            responseBody = "{\"id\":1,\"name\":\"Alice Updated\"}",
            durationMs = 150
        ),
        ApiLog(
            method = ApiMethod.DELETE,
            baseUrlType = "Production",
            baseUrl = "https://api.example.com",
            url = "/users/2",
            requestHeaders = "Authorization: Bearer token123",
            requestBody = "",
            responseCode = 204,
            responseBody = "",
            durationMs = 85
        )
    )

    NetworkLogDebugScreen(
        state = NetworkLogState(
            apiLog = mockApiLogs
        )
    )
}

@Composable
fun NetworkLogDebugScreenRoute() {
    val viewModel: NetworkLogViewModel = hiltViewModel()
    val state: NetworkLogState by viewModel.collectAsState()

    NetworkLogDebugScreen(state = state)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetworkLogDebugScreen(state: NetworkLogState) {
    Scaffold(
        containerColor = WepliTheme.color.black,
        topBar = {
            WepliAppBar(
                containerColor = Color.Transparent,
                title = "네트워크 로그",
                showBackButton = true
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            NoticeComponent(
                modifier = Modifier.padding(horizontal = 20.dp).padding(top = 20.dp)
            )

            MethodTagList(
                selectedTag = state.selectedTag,
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .padding(start = 20.dp)
            )

            ApiResultList(
                apiLog = state.apiLog,
                modifier = Modifier.padding(horizontal = 20.dp),
            )
        }
    }
}

@Composable
fun MethodTagList(selectedTag: ApiMethodUiModel, modifier: Modifier = Modifier) {
    val tagScrollState = rememberScrollState()

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
        modifier = modifier.horizontalScroll(tagScrollState),
    ) {
        ApiMethodUiModel.entries.forEach {
            MethodTag(tagName = it.name, isSelected = selectedTag == it)
        }
    }
}

@Composable
fun ApiResultList(apiLog: List<ApiLog>, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        apiLog.forEach {
            ApiResultComponent(apiLog = it)
        }
    }
}

@Preview
@Composable
fun NoticeComponent(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(WepliTheme.color.gray000)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(CoreR.drawable.ic_info_vector),
            tint = WepliTheme.color.gray900,
            contentDescription = null,
        )

        Text(
            text = "최근 20개 내역만 보여집니다.",
            style = WepliTheme.typo.body6,
            color = WepliTheme.color.gray900,
        )
    }
}