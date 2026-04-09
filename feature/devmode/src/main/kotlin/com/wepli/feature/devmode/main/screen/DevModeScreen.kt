package com.wepli.feature.devmode.main.screen

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.WepliAppBar
import com.wepli.feature.devmode.R
import com.wepli.feature.devmode.main.utils.DevModeUtil
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import template.menu.ExpandableMenuComponent
import template.menu.MenuComponent
import template.menu.MenuTitleComponent
import template.menu.ShortContentMenuComponent
import template.menu.SwitchMenuComponent
import theme.WepliTheme

@Preview(heightDp = 1500)
@Composable
private fun DevModeScreenPreview() {
    DevModeScreen(DevModeMainState(), {},  {}, {}, {})
}

@Composable
fun DevModeScreenRoute(
    navOnBack: () -> Unit,
    navOnNetworkLog: () -> Unit,
    navOnSendFcmPush: () -> Unit,
) {
    val viewModel: DevModeMainViewModel = hiltViewModel()
    val state by viewModel.collectAsState()
    val activity = LocalActivity.current as? ComponentActivity ?: return
    val metrics = activity.resources?.displayMetrics

    viewModel.collectSideEffect { effect ->
        when(effect) {
            DevModeMainEffect.RestartApplication -> DevModeUtil.restartApplication(activity)
        }
    }

    LaunchedEffect(metrics) {
        viewModel.processIntent(
            DevModeMainIntent.Init(
                androidOs = DevModeUtil.getAndroidOS(),
                sdkVersion = DevModeUtil.getSdkVersion(),
                deviceModel = DevModeUtil.getDeviceModel(),
                resourceBucket = DevModeUtil.getDeviceResourceBucket(metrics),
                density = DevModeUtil.getDpi(metrics),
                deviceWidth = DevModeUtil.getDeviceWidth(activity),
                deviceHeight = DevModeUtil.getDeviceHeight(activity),
            )
        )
    }

    DevModeScreen(
        state = state,
        sendAction = viewModel::processIntent,
        navOnBack = navOnBack,
        navOnNetworkLog = navOnNetworkLog,
        navOnSendFcmPush = navOnSendFcmPush,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DevModeScreen(
    state: DevModeMainState,
    sendAction: (DevModeMainIntent) -> Unit,
    navOnBack: () -> Unit,
    navOnNetworkLog: () -> Unit,
    navOnSendFcmPush: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = WepliTheme.color.black,
        topBar = {
            WepliAppBar(
                containerColor = Color.Black,
                title = stringResource(R.string.dev_mode_api_main_title),
                showBackButton = true,
                onClickBack = navOnBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(paddingValues)
                .padding(bottom = 50.dp)
        ) {
            UserInfoLayout(state)
            FcmPushLayout(sendAction, navOnSendFcmPush)
            DeviceInfoLayout(state)
            ScreenInfoLayout(state)
            LogMenuLayout(state, sendAction, navOnNetworkLog)
        }
    }
}

@Composable
private fun LogMenuLayout(
    state: DevModeMainState,
    sendAction: (DevModeMainIntent) -> Unit,
    navOnNetworkLog: () -> Unit,
) {
    MenuTitleComponent(title = "로그")
    MenuComponent(title = "네트워크 로그", onClickMenu = { navOnNetworkLog() })
    SwitchMenuComponent(
        title = "ScreenNameViewer 활성화",
        checked = state.isEnabledScreenNameViewer,
        onClickMenu = {},
        onCheckedChanged = { newState ->
            sendAction(DevModeMainIntent.ChangeScreenNameViewerState(newState))
        }
    )
    SwitchMenuComponent(
        title = "ComposableNametag 활성화",
        checked = state.isEnabledComposableNametag,
        onClickMenu = {},
        onCheckedChanged = { newState ->
            sendAction(DevModeMainIntent.ChangeComposableNametagState(newState))
        }
    )
}

@Composable
private fun FcmPushLayout(
    sendAction: (DevModeMainIntent) -> Unit,
    navOnSendFcmPush: () -> Unit,
) {
    MenuTitleComponent(title = "FCM")
    MenuComponent(
        title = "테스트 푸시 전송",
        onClickMenu = {
            sendAction(DevModeMainIntent.SendTestFcmMessage)
        }
    )
    MenuComponent(
        title = "FCM 푸시 생성",
        onClickMenu = {
            navOnSendFcmPush()
        }
    )
}

@Composable
private fun UserInfoLayout(state: DevModeMainState) {
    MenuTitleComponent(title = "사용자 정보")
    ExpandableMenuComponent(
        title = "액세스 토큰",
        content = state.accessToken
    )

    ExpandableMenuComponent(
        title = "리프레시 토큰",
        content = state.refreshToken
    )

    ExpandableMenuComponent(
        title = "FCM 토큰",
        content = state.fcmToken
    )

    ExpandableMenuComponent(
        title = "FCM 액세스 토큰",
        content = state.fcmAccessToken
    )
}

@Composable
private fun DeviceInfoLayout(state: DevModeMainState) {
    MenuTitleComponent(title = "디바이스 정보")
    ShortContentMenuComponent(
        title = "OS 버전 (SDK)",
        content = "Android ${state.androidOs} (${state.sdkVersion})"
    )

    ShortContentMenuComponent(
        title = "모델명",
        content = state.deviceModel
    )
}

@Composable
private fun ScreenInfoLayout(state: DevModeMainState) {
    MenuTitleComponent(title = "화면 정보")
    ShortContentMenuComponent(
        title = "화면 비율",
        content = "${state.deviceWidth} x ${state.deviceHeight}"
    )

    ShortContentMenuComponent(
        title = "화면 밀도",
        content = "${state.density} dpi"
    )

    ShortContentMenuComponent(
        title = "리소스 버킷",
        content = state.resourceBucket
    )
}