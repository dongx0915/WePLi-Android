package com.wepli.feature.devmode.main.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import appbar.WepliAppBar
import com.wepli.feature.devmode.R
import template.menu.MenuComponent
import template.menu.MenuSwitchComponent
import template.menu.MenuTitleComponent
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
            modifier = Modifier.padding(paddingValues))
        {
            LogMenuLayout()
        }
        paddingValues
    }
}

@Composable
private fun ColumnScope.LogMenuLayout() {
    MenuTitleComponent(title = "로그")
    MenuComponent(title = "네트워크 로그", onClickMenu = {  })
    MenuSwitchComponent(title = "ScreenNameViewer 활성화", checked = false, onClickMenu = {})
}