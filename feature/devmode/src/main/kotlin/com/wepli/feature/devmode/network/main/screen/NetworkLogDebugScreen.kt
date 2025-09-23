package com.wepli.feature.devmode.network.main.screen

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.WepliAppBar
import com.wepli.feature.devmode.R
import com.wepli.feature.devmode.mock.mockApiLogs
import com.wepli.feature.devmode.network.main.component.ApiResultComponent
import com.wepli.feature.devmode.network.main.component.MethodTag
import com.wepli.feature.devmode.network.main.enums.ApiMethodUiTag
import com.wepli.feature.devmode.network.main.viewmodel.NetworkLogIntent
import com.wepli.feature.devmode.network.main.viewmodel.NetworkLogState
import com.wepli.feature.devmode.network.main.viewmodel.NetworkLogViewModel
import org.orbitmvi.orbit.compose.collectAsState
import theme.WepliTheme
import com.wepli.core.resources.R as CoreR


@Preview
@Composable
fun NetworkLogDebugScreenPreview() {
    NetworkLogDebugScreen(
        state = NetworkLogState(
            originApiLogs = mockApiLogs
        ),
        navOnNetworkLogDetail = {},
        navOnBack = {},
        sendAction = {}
    )
}

@Composable
fun NetworkLogDebugScreenRoute(
    navOnNetworkLogDetail: (Int) -> Unit,
    navOnBack: () -> Unit
) {
    val viewModel: NetworkLogViewModel = hiltViewModel()
    val state: NetworkLogState by viewModel.collectAsState()

    NetworkLogDebugScreen(
        state = state,
        navOnNetworkLogDetail = navOnNetworkLogDetail,
        navOnBack = navOnBack,
        sendAction = viewModel::processIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NetworkLogDebugScreen(
    state: NetworkLogState,
    navOnNetworkLogDetail: (Int) -> Unit,
    navOnBack: () -> Unit,
    sendAction: (NetworkLogIntent) -> Unit,
) {
    val scrollState = rememberLazyListState()

    Scaffold(
        containerColor = WepliTheme.color.black,
        topBar = {
            WepliAppBar(
                containerColor = Color.Transparent,
                title = stringResource(R.string.dev_mode_api_log_title),
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
                        maxLogCount = state.maxApiLogs,
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
                        modifier = Modifier
                            .clickable { navOnNetworkLogDetail(log.id) }
                            .padding(start = 20.dp, end = 20.dp, bottom = 12.dp),
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
        modifier = modifier.fillMaxWidth().horizontalScroll(tagScrollState),
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

@Composable
fun NoticeComponent(
    maxLogCount: Int,
    modifier: Modifier = Modifier
) {
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
            text = stringResource(R.string.dev_mode_api_log_limit_notice, maxLogCount),
            style = WepliTheme.typo.body6,
            color = WepliTheme.color.gray900,
        )
    }
}