package com.wepli.feature.namecard.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.WepliAppBar
import button.WepliBasicButton
import button.WepliButtonStyle
import com.wepli.feature.namecard.component.NameCardComponent
import com.wepli.feature.namecard.main.mvi.NameCardMainUiState
import com.wepli.feature.namecard.main.viewmodel.NameCardMainViewModel
import org.orbitmvi.orbit.compose.collectAsState
import theme.WepliTheme

@Composable
fun NameCardScreenRoute(
    navOnBack: () -> Unit,
    navOnNameCardDetail: () -> Unit,
) {
    val viewModel: NameCardMainViewModel = hiltViewModel()
    val state by viewModel.collectAsState()

    NameCardScreen(
        state = state,
        navOnBack = navOnBack,
        navOnNameCardDetail = navOnNameCardDetail
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NameCardScreen(
    state: NameCardMainUiState,
    navOnBack: () -> Unit,
    navOnNameCardDetail: () -> Unit,
) {
    Scaffold(
        topBar = {
            WepliAppBar(
                title = "",
                showBackButton = true,
                onClickBack = { navOnBack() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = WepliTheme.color.black)
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .padding(bottom = 20.dp)
        ) {
            Spacer(modifier = Modifier.padding(top = 56.dp))
            Text(
                text = "내 취향 명함 만들기",
                style = WepliTheme.typo.title1,
                color = WepliTheme.color.gray900
            )
            Spacer(modifier = Modifier.padding(top = 12.dp))
            Text(
                text = "${state.user.nickname}님의 취향이 드러나는 명함을 만들어드려요",
                style = WepliTheme.typo.body4,
                color = WepliTheme.color.gray500
            )

            Spacer(modifier = Modifier.weight(1f))
            NameCardComponent(
                nameCardInfo = state.nameCardInfo,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.weight(1f))
            WepliBasicButton(
                title = "시작하기",
                isEnabled = true,
                onClick = { navOnNameCardDetail() },
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .align(Alignment.CenterHorizontally),
                buttonStyle = WepliButtonStyle.Basic,
            )
        }
    }
}

@Preview
@Composable
fun NameCardMainScreenPreview() {
    NameCardScreen(
        state = NameCardMainUiState(),
        navOnBack = { },
        navOnNameCardDetail = { }
    )
}