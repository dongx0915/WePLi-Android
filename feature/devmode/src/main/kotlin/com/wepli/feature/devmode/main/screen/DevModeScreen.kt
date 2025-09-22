package com.wepli.feature.devmode.main.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import appbar.WepliAppBar
import com.wepli.feature.devmode.R
import theme.WepliTheme


@Composable
fun DevModeScreenRoute() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DevModeScreen(
    navOnBack: () -> Unit
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

        paddingValues
    }
}