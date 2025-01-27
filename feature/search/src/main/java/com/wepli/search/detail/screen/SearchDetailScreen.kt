package com.wepli.search.detail.screen

import android.annotation.SuppressLint
import android.app.appsearch.SearchResults
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.WepliAppBar
import button.WepliBasicButton
import com.wepli.search.detail.state.SearchDetailEffect
import com.wepli.search.detail.state.SearchDetailIntent
import com.wepli.search.detail.state.SearchDetailUiState
import com.wepli.search.detail.viewmodel.SearchDetailViewModel
import com.wepli.search.navigation.SearchScreenMode
import com.wepli.shared.feature.mock.songMockData
import com.wepli.uimodel.music.SongUiData
import common.WepliSpacer
import extensions.compose.shimmerEffect
import extensions.compose.toPx
import image.AsyncImageWithPreview
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import textfield.WepliTextField
import textfield.WepliTextFieldType
import theme.WepliTheme

@Composable
fun SearchScreenRoute(
    screenMode: SearchScreenMode,
    searchQuery: String,
    navOnBack: () -> Unit,
) {
    val viewModel = hiltViewModel<SearchDetailViewModel>()
    val state: SearchDetailUiState by viewModel.collectAsState()
    val scrollState = rememberLazyListState()
    val context = LocalContext.current

    // 초기 상태 설정 및 검색 요청
    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotEmpty()) {
            viewModel.processIntent(SearchDetailIntent.OnSearchQueryChanged(searchQuery))
            viewModel.processIntent(SearchDetailIntent.RequestSearch(searchQuery))
        }
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SearchDetailEffect.SearchError -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    SearchScreen(
        screenMode = screenMode,
        onQueryUpdate = { viewModel.processIntent(SearchDetailIntent.OnSearchQueryChanged(it)) },
        onEnter = { viewModel.processIntent(SearchDetailIntent.RequestSearch(state.searchInput)) },
        searchQuery = state.searchInput,
        searchResult = state.searchMusicResult,
        lazyListState = scrollState,
        navOnBack = { navOnBack() }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    screenMode: SearchScreenMode,
    onQueryUpdate: (String) -> Unit,
    onEnter: () -> Unit,
    searchQuery: String,
    searchResult: List<SongUiData>,
    lazyListState: LazyListState,
    navOnBack: () -> Unit,
) {
    LaunchedEffect(searchResult) {
        lazyListState.scrollToItem(0)
    }

    Scaffold(
        containerColor = WepliTheme.color.black,
        topBar = {
            WepliAppBar(
                showLogo = false,
                showBackButton = true,
                title = "곡 검색",
                onClickBack = { navOnBack() }
            )
        }
    ) { paddingValues ->
        when(screenMode) {
            SearchScreenMode.NORMAL -> {
                SearchContent(
                    paddingValues = paddingValues,
                    searchQuery = searchQuery,
                    searchResult = searchResult,
                    onQueryUpdate = onQueryUpdate,
                    onEnter = onEnter,
                    lazyListState = lazyListState,
                )
            }
            SearchScreenMode.SELECTABLE -> {
                SearchWithSelectedSheet(
                    paddingValues = paddingValues,
                    searchQuery = searchQuery,
                    searchResult = searchResult,
                    onQueryUpdate = onQueryUpdate,
                    onEnter = onEnter,
                    lazyListState = lazyListState,
                )
            }
        }
    }
}

@Composable
fun SearchContent(
    paddingValues: PaddingValues,
    searchQuery: String,
    searchResult: List<SongUiData>,
    onQueryUpdate: (String) -> Unit,
    onEnter: () -> Unit,
    lazyListState: LazyListState
) {
    Column(
        modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(horizontal = 20.dp)
    ) {
        SearchBar(
            searchQuery = searchQuery,
            onQueryUpdate = onQueryUpdate,
            onEnter = onEnter
        )

        SearchResults(
            searchResult = searchResult,
            lazyListState = lazyListState
        )
    }
}

@Composable
fun SearchWithSelectedSheet(
    paddingValues: PaddingValues,
    searchQuery: String,
    searchResult: List<SongUiData>,
    onQueryUpdate: (String) -> Unit,
    onEnter: () -> Unit,
    lazyListState: LazyListState,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SearchContent(
            paddingValues = paddingValues,
            searchQuery = searchQuery,
            searchResult = searchResult,
            onQueryUpdate = onQueryUpdate,
            onEnter = onEnter,
            lazyListState = lazyListState,
        )

        SelectedSheet(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
        )
    }
}

@Composable
fun SearchBar(
    searchQuery: String,
    onQueryUpdate: (String) -> Unit,
    onEnter: () -> Unit,
) {
    Box(modifier = Modifier.padding(vertical = 10.dp)) {
        WepliTextField(
            value = searchQuery,
            singleLine = true,
            onValueChanged = { onQueryUpdate(it) },
            onEnter = { onEnter() },
            placeholder = "검색어를 입력하세요.",
            type = WepliTextFieldType.Search
        )
    }
}

@Composable
fun SearchResults(
    searchResult: List<SongUiData>,
    lazyListState: LazyListState,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 24.dp)
    ) {
        items(count = searchResult.size, key = { searchResult[it].id }) { idx ->
            SongItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                songUiData = searchResult[idx]
            )
        }
    }
}

@Composable
fun SongItem(
    modifier: Modifier = Modifier,
    songUiData: SongUiData,
) {
    val imageSize = 52.dp.toPx()
    val imageUrl = remember(songUiData.id) { songUiData.getImageUrl(imageSize) }

    Row(modifier = modifier) {
        AsyncImageWithPreview(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(4.dp)),
            imageUrl = imageUrl,
            contentScale = ContentScale.Crop,
            imageOverrideSize = 52.dp,
            loadingContent = { SkeletonImage() },
        )

        WepliSpacer(horizontal = 12.dp)
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = songUiData.title,
                style = WepliTheme.typo.body4,
                color = WepliTheme.color.white,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            WepliSpacer(vertical = 4.dp)
            Text(
                text = songUiData.artistName,
                style = WepliTheme.typo.body6,
                color = WepliTheme.color.gray600,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun SelectedSheet(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(color = WepliTheme.color.black)
            .padding(top = 12.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 12.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            repeat(10) {
                SelectedSongItem(songMockData[it].title, songMockData[it].getImageUrl(40.dp.toPx()))
            }
        }

        WepliBasicButton(
            title = "추가하기",
            isEnabled = true,
            onClick = { /* TODO */ },
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}

@Composable
fun SelectedSongItem(
    title: String,
    imageUrl: String,
) {
    Box {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(100.dp))
                .matchParentSize()
                .alpha(0.5f)
                .border(brush = WepliTheme.color.linear3, shape = CircleShape, width = 1.dp)
                .padding(top = 6.dp, bottom = 6.dp, start = 6.dp, end = 12.dp),
        )

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(100.dp))
                .background(brush = WepliTheme.color.linear3, alpha = 0.1f)
                .padding(top = 6.dp, bottom = 6.dp, start = 6.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImageWithPreview(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .border(brush = WepliTheme.color.linear3, shape = CircleShape, width = 1.dp),
                imageUrl = imageUrl,
                contentScale = ContentScale.Crop,
                imageOverrideSize = 40.dp,
                loadingContent = { SkeletonImage() },
            )

            Text(
                text = title,
                style = WepliTheme.typo.body6,
                color = WepliTheme.color.white,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
fun SkeletonImage() {
    Box(
        modifier = Modifier
            .background(color = WepliTheme.color.gray500)
            .size(52.dp)
            .clip(RoundedCornerShape(4.dp))
            .shimmerEffect(4.dp)
    )
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(SearchScreenMode.NORMAL, {}, {}, "", songMockData, rememberLazyListState(), {})
}