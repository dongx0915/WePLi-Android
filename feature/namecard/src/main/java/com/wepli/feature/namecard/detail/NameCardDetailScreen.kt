package com.wepli.feature.namecard.detail

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.WepliAppBar
import com.wepli.feature.namecard.detail.chapter.PhotoCardChapter1Screen
import com.wepli.feature.namecard.detail.chapter.PhotoCardChapter3Screen
import com.wepli.feature.namecard.detail.chapter.PhotoCardChapter2Screen
import com.wepli.feature.namecard.detail.component.PhotoCardLoadingComponent
import com.wepli.feature.namecard.detail.mvi.PhotoCardDetailEffect
import com.wepli.feature.namecard.detail.mvi.PhotoCardDetailIntent
import com.wepli.feature.namecard.detail.mvi.PhotoCardDetailUiState
import com.wepli.feature.namecard.detail.viewmodel.PhotoCardDetailViewModel
import com.wepli.shared.feature.uimodel.namecard.PhotoCardUiData
import com.wepli.uimodel.music.SongUiData
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import theme.WepliTheme

@Preview
@Composable
fun NameCardDetailScreenPreview() {
    NameCardDetailScreen(state = PhotoCardDetailUiState(), {}, {}, {})
}

@Composable
fun NameCardDetailScreenRoute(
    selectedSong: SongUiData?,
    navOnBack: () -> Unit,
    navOnSongSearchScreen: () -> Unit,
    navOnResultScreen: (nameCardInfo: PhotoCardUiData) -> Unit,
) {
    val viewModel: PhotoCardDetailViewModel = hiltViewModel()
    val state: PhotoCardDetailUiState by viewModel.collectAsState()

    viewModel.collectSideEffect {
        when (it) {
            PhotoCardDetailEffect.NavigateBack -> navOnBack()
            is PhotoCardDetailEffect.OnCompleteChapter -> navOnResultScreen(it.photoCardResult)
        }
    }

    selectedSong?.let {
        LaunchedEffect(selectedSong) {
            viewModel.processIntent(PhotoCardDetailIntent.OnFavoriteSongSelected(it))
        }
    }

    NameCardDetailScreen(state, viewModel::processIntent, navOnBack, navOnSongSearchScreen)
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NameCardDetailScreen(
    state: PhotoCardDetailUiState,
    sendAction: (PhotoCardDetailIntent) -> Unit,
    navOnBack: () -> Unit,
    navOnSongSearchScreen: () -> Unit,
) {
    val navOnNextPage: () -> Unit = { sendAction(PhotoCardDetailIntent.OnNextPage) }
    val navOnPreviousPage: () -> Unit = { sendAction(PhotoCardDetailIntent.OnPreviousPage) }
    val pageList: List<@Composable () -> Unit> = remember(state) {
        listOf(
            { PhotoCardChapter1Screen(state = state, navOnNextPage = navOnNextPage, navOnSongSearchScreen = navOnSongSearchScreen) },
            { PhotoCardChapter2Screen(state = state, sendAction = sendAction, navOnNextPage = navOnNextPage) },
            { PhotoCardChapter3Screen(state = state, sendAction = sendAction, navOnNextPage = navOnNextPage) },
        )
    }
    val pagerState = rememberPagerState { pageList.size }

    LaunchedEffect(Unit) {
        sendAction(PhotoCardDetailIntent.Initialize(totalPage = pageList.size, oneLineIntroMaxLength = 20))
    }

    LaunchedEffect(state.currentPage) {
        pagerState.animateScrollToPage(state.currentPage)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.blur( // 로딩 중일 때는 화면을 블러처리
                if(state.isLoading) 16.dp else 0.dp
            ),
            topBar = {
                Column {
                    WepliAppBar(
                        title = "",
                        showBackButton = true,
                        onClickBack = { navOnPreviousPage() }
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

        if (state.isLoading) {
            PhotoCardLoadingComponent(state)
        }
    }

    BackHandler(state.currentPage != 0) {
        sendAction(PhotoCardDetailIntent.OnPreviousPage)
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