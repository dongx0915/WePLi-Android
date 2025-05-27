package com.wepli.mypage.devmode.network.main.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import com.wepli.mypage.devmode.network.main.component.ApiResultComponent
import com.wepli.mypage.devmode.network.main.component.MethodTag
import com.wepli.mypage.devmode.network.main.enums.ApiMethodUiTag
import com.wepli.mypage.devmode.network.main.viewmodel.NetworkLogIntent
import com.wepli.mypage.devmode.network.main.viewmodel.NetworkLogState
import com.wepli.mypage.devmode.network.main.viewmodel.NetworkLogViewModel
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
            startTime = System.currentTimeMillis(),
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
            startTime = System.currentTimeMillis(),
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
            startTime = System.currentTimeMillis(),
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
            startTime = System.currentTimeMillis(),
            durationMs = 85
        )
    )

    NetworkLogDebugScreen(
        state = NetworkLogState(
            originApiLogs = mockApiLogs
        ),
        navOnBack = {},
        sendAction = {}
    )
}

@Composable
fun NetworkLogDebugScreenRoute(navOnBack: () -> Unit) {
    val viewModel: NetworkLogViewModel = hiltViewModel()
    val state: NetworkLogState by viewModel.collectAsState()

    NetworkLogDebugScreen(state = state, navOnBack = navOnBack, sendAction = viewModel::processIntent)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NetworkLogDebugScreen(
    state: NetworkLogState,
    navOnBack: () -> Unit,
    sendAction: (NetworkLogIntent) -> Unit,
) {
    val scrollState = rememberLazyListState()

    Scaffold(
        containerColor = WepliTheme.color.black,
        topBar = {
            WepliAppBar(
                containerColor = Color.Transparent,
                title = "네트워크 로그",
                showBackButton = true,
                onClickBack = navOnBack
            )
        }
    ) { paddingValues ->
        CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
            LazyColumn(
                state = scrollState,
                modifier = Modifier.padding(paddingValues)
            ) {
                item {
                    NoticeComponent(
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp)
                    )
                }

                stickyHeader {
                    MethodTagList(
                        selectedTag = state.selectedTag,
                        modifier = Modifier
                            .background(WepliTheme.color.black)
                            .padding(top = 20.dp, bottom = 20.dp, start = 20.dp),
                        onClick = {
                            sendAction(NetworkLogIntent.SelectTag(it))
                        }
                    )
                }

                itemsIndexed(state.filteredApiLogs) { _, log ->
                    ApiResultComponent(
                        apiLog = log,
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 12.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MethodTagList(
    selectedTag: ApiMethodUiTag,
    onClick: (ApiMethodUiTag) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tagScrollState = rememberScrollState()

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
        modifier = modifier.horizontalScroll(tagScrollState),
    ) {
        ApiMethodUiTag.entries.forEach {
            MethodTag(
                tagName = it.name,
                isSelected = selectedTag == it,
                modifier = Modifier.clickable { onClick(it) }
            )
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