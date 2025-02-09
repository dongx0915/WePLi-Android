package com.wepli.feature.namecard.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import appbar.WepliAppBar
import com.wepli.feature.namecard.detail.chapter.NameCardChapterOneScreen
import com.wepli.feature.namecard.detail.chapter.NameCardChapterThreeScreen
import com.wepli.feature.namecard.detail.chapter.NameCardChapterTwoScreen
import theme.WepliTheme

@Preview
@Composable
fun NameCardDetailScreenPreview() {
    NameCardDetailScreen({}, {})
}

@Composable
fun NameCardDetailScreenRoute(
    navOnBack: () -> Unit,
    navOnSongSearchScreen: () -> Unit,
) {
    NameCardDetailScreen(navOnBack, navOnSongSearchScreen)
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NameCardDetailScreen(
    navOnBack: () -> Unit,
    navOnSongSearchScreen: () -> Unit,
) {
    val pageList: List<@Composable () -> Unit> = remember {
        listOf(
            { NameCardChapterOneScreen(navOnSongSearchScreen = navOnSongSearchScreen) },
            { NameCardChapterTwoScreen() },
            { NameCardChapterThreeScreen() },
        )
    }
    val pagerState = rememberPagerState { pageList.size }

    Scaffold(
        topBar = {
            Column {
                WepliAppBar(
                    title = "",
                    showBackButton = true
                )
                NameCardProgressBar(currentPage = pagerState.currentPage, totalPage = pageList.size)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = WepliTheme.color.black)
                .padding(paddingValues)
                .padding(top = 56.dp, bottom = 20.dp)
        ) {
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false
            ) {
                pageList[it]()
            }
        }
    }
}

@Composable
fun NameCardProgressBar(currentPage: Int, totalPage: Int) {
    val configuration = LocalConfiguration.current
    val progressStepWidth = remember {
        val screenWidth = configuration.screenWidthDp.dp
        screenWidth / totalPage
    }
    val progress = remember(currentPage) { progressStepWidth * (currentPage + 1) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height((1.5).dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(WepliTheme.color.gray100)
        )

        Box(
            modifier = Modifier
                .size(progress)
                .background(WepliTheme.color.linear3)
        )
    }
}