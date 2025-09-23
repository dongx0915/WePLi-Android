package com.wepli.feature.devmode.main.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import appbar.WepliAppBar
import com.wepli.feature.devmode.R
import template.menu.ExpandableMenuComponent
import template.menu.MenuComponent
import template.menu.SwitchMenuComponent
import template.menu.MenuTitleComponent
import template.menu.ShortContentMenuComponent
import theme.WepliTheme


@Composable
fun DevModeScreenRoute(
    navOnBack: () -> Unit,
    navOnNetworkLog: () ->Unit,
) {
    DevModeScreen(navOnBack, navOnNetworkLog)
}

@Preview
@Composable
private fun DevModeScreenPreview() {
    DevModeScreen({}, {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DevModeScreen(
    navOnBack: () -> Unit,
    navOnNetworkLog: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = WepliTheme.color.black,
        topBar = {
            WepliAppBar(
                containerColor = Color.Transparent,
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
        ) {
            UserInfoLayout()
            DeviceInfoLayout()
            LogMenuLayout(navOnNetworkLog)
        }
    }
}

@Composable
private fun LogMenuLayout(
    navOnNetworkLog: () -> Unit,
) {
    MenuTitleComponent(title = "로그")
    MenuComponent(title = "네트워크 로그", onClickMenu = { navOnNetworkLog() })
    SwitchMenuComponent(title = "ScreenNameViewer 활성화", checked = false, onClickMenu = {})
}

@Composable
private fun UserInfoLayout() {
    MenuTitleComponent(title = "사용자 정보")
    ExpandableMenuComponent(
        title = "액세스 토큰",
        content = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIxMjM0NTY3ODkwIiwidXNlcm5hbWUiOiJ3ZWxjb21lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
    )

    ExpandableMenuComponent(
        title = "리프레시 토큰",
        content = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIxMjM0NTY3ODkwIiwidXNlcm5hbWUiOiJ3ZWxjb21lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
    )

    ExpandableMenuComponent(
        title = "FCM 토큰",
        content = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIxMjM0NTY3ODkwIiwidXNlcm5hbWUiOiJ3ZWxjb21lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
    )
}

@Composable
private fun DeviceInfoLayout() {
    MenuTitleComponent(title = "디바이스 정보")
    ShortContentMenuComponent(
        title = "OS 버전 (SDK)",
        content = "Android 13 (33)"
    )

    ShortContentMenuComponent(
        title = "모델명",
        content = "SM-F916N"
    )

    ShortContentMenuComponent(
        title = "리소스 버킷",
        content = "xxhdpi"
    )
}