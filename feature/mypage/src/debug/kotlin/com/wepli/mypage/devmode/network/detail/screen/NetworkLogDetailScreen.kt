package com.wepli.mypage.devmode.network.detail.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.WepliAppBar
import com.sebastianneubauer.jsontree.JsonTree
import com.sebastianneubauer.jsontree.TreeColors
import com.wepli.mypage.devmode.mock.mockApiLogs
import com.wepli.mypage.devmode.network.detail.viewmodel.NetworkLogDetailIntent
import com.wepli.mypage.devmode.network.detail.viewmodel.NetworkLogDetailState
import com.wepli.mypage.devmode.network.detail.viewmodel.NetworkLogDetailViewModel
import com.wepli.mypage.devmode.network.main.component.StatusTag
import com.wepli.mypage.devmode.network.main.enums.toColor
import debug.model.ApiLog
import extensions.toPrettyJsonString
import org.orbitmvi.orbit.compose.collectAsState
import theme.WepliTheme
import com.wepli.designsystem.R as CoreR

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

    val scrollState = rememberScrollState()

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
                .padding(horizontal = 20.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ApiInfoHeader(apiLog = state.apiLog, modifier = Modifier.padding(top = 20.dp))

            ApiRequestComponent(
                apiLog = state.apiLog,
                modifier = Modifier.padding(top = 40.dp)
            )

            ApiResponseComponent(
                apiLog = state.apiLog,
                modifier = Modifier.padding(top = 40.dp)
            )
        }
    }
}

@Composable
private fun ApiInfoHeader(apiLog: ApiLog, modifier: Modifier = Modifier) {
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
private fun ApiRequestComponent(apiLog: ApiLog, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Request",
            style = WepliTheme.typo.subTitle2,
            color = WepliTheme.color.gray900,
        )

        CollapsingComponent(title = "Headers", contents = apiLog.requestHeaders.toPrettyJsonString())
        CollapsingComponent(title = "Body", contents = apiLog.requestBody)
    }
}

@Composable
private fun ApiResponseComponent(apiLog: ApiLog, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Response",
            style = WepliTheme.typo.subTitle2,
            color = WepliTheme.color.gray900,
        )

        CollapsingComponent(title = "Body", contents = apiLog.responseBody)
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

@Composable
private fun CollapsingComponent(
    title: String,
    contents: String,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "ArrowRotation"
    )

    Column(
        modifier = modifier
            .animateContentSize()
            .clickable { expanded = !expanded } // 클릭 시 toggle
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(color = WepliTheme.color.gray050)
            .border(width = 1.dp, color = WepliTheme.color.gray100)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                style = WepliTheme.typo.body1,
                color = WepliTheme.color.gray700,
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = ImageVector.vectorResource(CoreR.drawable.ic_arrow_down_vector),
                tint = WepliTheme.color.gray400,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .rotate(rotationAngle)
            )
        }

        if (expanded) {
            // Body
            JsonTree(
                json = contents,
                onLoading = {},
                colors = TreeColors(
                    keyColor = Color(0xFF9CDCFE),               // 키: 밝은 파란색
                    stringValueColor = Color(0xFFD69D85),       // 문자열 값: 부드러운 오렌지
                    numberValueColor = Color(0xFFB5CEA8),       // 숫자 값: 연한 녹색
                    booleanValueColor = Color(0xFFD7BA7D),      // 불리언 값: 노란색
                    nullValueColor = Color(0xFF808080),         // null 값: 회색
                    indexColor = Color(0xFFB5CEA8),             // 배열 인덱스: 숫자와 동일한 색상
                    symbolColor = Color(0xFFAAAAAA),            // 기호(콜론, 괄호 등): 연한 회색
                    iconColor = Color(0xFF6A9955),              // 아이콘(접기/펼치기): 녹색
                    selectedHighlightColor = Color(0xFF264F78), // 선택된 항목 강조: 파란색 오버레이
                    highlightColor = Color(0xFF3E4451)          // 하이라이트 배경: 미묘한 회색
                ),
                textStyle = WepliTheme.typo.body6,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 600.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = WepliTheme.color.gray100)
                    .padding(vertical = 12.dp, horizontal = 16.dp)
            )

            // Footer
        }
    }
}