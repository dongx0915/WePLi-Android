package com.wepli.mypage.menus.appinfo.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import appbar.WepliAppBar
import com.wepli.mypage.component.MenuComponent
import com.wepli.mypage.component.MenuTitleComponent
import theme.WepliTheme

@Preview
@Composable
fun AppInfoScreenPreview() {
    AppInfoScreen {}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppInfoScreen(
    navOnBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = WepliTheme.color.black,
        topBar = {
            WepliAppBar(
                title = "공지 및 이용약관",
                showBackButton = true,
                onClickBack = { navOnBack() }
            )
        }
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .padding(bottom = 40.dp)
                .verticalScroll(scrollState)
                .fillMaxSize(),
        ) {
            ServiceInquiryMenuLayout()
            TermsAndPolicyMenuLayout()
        }
    }
}


@Composable
fun ServiceInquiryMenuLayout() {
    Column {
        MenuTitleComponent(title = "서비스 문의")
        MenuComponent(title = "문의 및 신고")
        MenuComponent(title = "피드백")
    }
}

@Composable
fun TermsAndPolicyMenuLayout() {
    Column {
        MenuTitleComponent(title = "공지 및 이용약관")
        MenuComponent(title = "공지사항")
        MenuComponent(title = "서비스 이용약관")
        MenuComponent(title = "개인정보 처리방침")
    }
}