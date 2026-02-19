package com.wepli.devmode.network.presentation.main.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wepli.devmode.network.R
import com.wepli.devmode.network.mock.mockApiLogs
import com.wepli.devmode.network.presentation.main.component.ApiResultComponent
import com.wepli.devmode.network.presentation.main.component.MethodTag
import com.wepli.devmode.network.presentation.main.component.NetworkLoggerAppBar
import com.wepli.devmode.network.presentation.main.enums.ApiMethodUiTag
import com.wepli.devmode.network.presentation.main.viewmodel.NetworkLogState
import com.wepli.devmode.network.presentation.main.viewmodel.NetworkLogViewModel
import com.wepli.devmode.network.theme.NetworkLogTheme
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import com.wepli.core.resources.R as CoreR


@Preview
@Composable
fun NetworkLogDebugScreenPreview() {
    NetworkLogDebugScreen(
        state = NetworkLogState(
            filteredApiLogs = mockApiLogs.groupBy { it.method.name },
        ),
        navOnNetworkLogDetail = {},
    )
}

@Composable
fun NetworkLogDebugScreenRoute(
    navOnNetworkLogDetail: (Int) -> Unit
) {
    val viewModel: NetworkLogViewModel = hiltViewModel()
    val state: NetworkLogState by viewModel.collectAsState()

    NetworkLogDebugScreen(
        state = state,
        navOnNetworkLogDetail = navOnNetworkLogDetail,
    )
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NetworkLogDebugScreen(
    state: NetworkLogState,
    navOnNetworkLogDetail: (Int) -> Unit,
) {
    val pagerState = rememberPagerState { ApiMethodUiTag.entries.size }
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = NetworkLogTheme.color.black,
        topBar = {
            NetworkLoggerAppBar(
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    ) { paddingValues ->
        CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                MethodTagHeader(
                    selectedTag = ApiMethodUiTag.entries[pagerState.currentPage],
                    modifier = Modifier
                        .background(NetworkLogTheme.color.black)
                        .padding(top = 20.dp, bottom = 20.dp),
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(ApiMethodUiTag.indexOf(it))
                        }
                    }
                )

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize(),
                ) { page ->
                    val apiMethod: String = ApiMethodUiTag.entries[page].name
                    val items = state.filteredApiLogs[apiMethod].orEmpty()

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        itemsIndexed(items) { index, log ->
                            ApiResultComponent(
                                apiLog = log,
                                modifier = Modifier
                                    .clickable { navOnNetworkLogDetail(log.id) }
                                    .padding(horizontal = 20.dp)
                                    .padding(bottom = 12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MethodTagHeader(
    selectedTag: ApiMethodUiTag,
    onClick: (ApiMethodUiTag) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()

    LaunchedEffect(selectedTag) {
        val index = selectedTag.ordinal

        listState.animateScrollToItem(index)
    }

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
        contentPadding = PaddingValues(horizontal = 20.dp),
        modifier = modifier.fillMaxWidth(),
    ) {
        items(ApiMethodUiTag.entries) { tag ->
            MethodTag(
                tagName = tag.name,
                isSelected = selectedTag == tag,
                modifier = Modifier.clickable { onClick(tag) }
            )
        }
    }
}