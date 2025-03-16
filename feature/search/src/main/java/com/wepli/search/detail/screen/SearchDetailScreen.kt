package com.wepli.search.detail.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.WepliAppBar
import button.WepliBasicButton
import button.WepliButtonStyle
import com.wepli.designsystem.R
import com.wepli.search.detail.mvi.SearchDetailEffect
import com.wepli.search.detail.mvi.SearchDetailIntent
import com.wepli.search.detail.mvi.SearchDetailUiState
import com.wepli.search.detail.viewmodel.SearchDetailViewModel
import com.wepli.search.navigation.SearchScreenMode
import com.wepli.shared.feature.mock.songMockData
import com.wepli.uimodel.music.SongUiData
import common.ShimmerSkeleton
import common.WepliSpacer
import custom.MusicItem
import custom.MusicItemType
import extensions.compose.toPx
import extensions.compose.topBorderWithRoundedCorners
import image.AsyncImageWithPreview
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import textfield.WepliTextField
import textfield.WepliTextFieldType
import theme.WepliTheme

private val SongItemImageSize = 52.dp

@Composable
fun SearchScreenRoute(
    screenMode: SearchScreenMode,
    searchQuery: String,
    navOnBack: () -> Unit,
    navigateBackWithSelectedSongs: (List<SongUiData>) -> Unit,
) {
    val viewModel = hiltViewModel<SearchDetailViewModel>()
    val state: SearchDetailUiState by viewModel.collectAsState()
    val context = LocalContext.current

    // 초기 상태 설정 및 검색 요청
    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotEmpty()) {
            viewModel.processIntent(
                SearchDetailIntent.OnSearchQueryChanged(searchQuery),
                SearchDetailIntent.RequestSearch(searchQuery)
            )
        }
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SearchDetailEffect.SearchError -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }
            is SearchDetailEffect.SelectedLimitExceeded -> {
                Toast.makeText(context, "최대 ${sideEffect.limit}곡까지 선택 가능합니다.", Toast.LENGTH_SHORT).show()
            }
            is SearchDetailEffect.NavigateBackWithResult -> {
                navigateBackWithSelectedSongs(sideEffect.selectedSongs)
            }
        }
    }

    SearchScreen(
        screenMode = screenMode,
        state = state,
        sendAction = { viewModel.processIntent(it) },
        navOnBack = { navOnBack() },
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    screenMode: SearchScreenMode,
    state: SearchDetailUiState,
    sendAction: (SearchDetailIntent) -> Unit,
    navOnBack: () -> Unit,
) {
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
        when (screenMode) {
            SearchScreenMode.Normal -> {
                SearchContent(
                    state = state,
                    paddingValues = paddingValues,
                    onClickSongItem = { /* TODO */},
                    sendAction = sendAction,
                )
            }

            is SearchScreenMode.Selectable -> {
                LaunchedEffect(screenMode.maxCount) {
                    sendAction(SearchDetailIntent.SetMaxSelectCount(screenMode.maxCount))
                }

                SearchWithSelectedSheet(
                    state = state,
                    paddingValues = paddingValues,
                    onClickSongItem = { sendAction(SearchDetailIntent.OnSongSelected(it)) },
                    sendAction = sendAction,
                )
            }
        }
    }
}

@Composable
fun SearchContent(
    state: SearchDetailUiState,
    paddingValues: PaddingValues,
    onClickSongItem: (SongUiData) -> Unit,
    sendAction: (SearchDetailIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 20.dp)
    ) {
        SearchBar(
            searchQuery = state.searchInput,
            onQueryUpdate = { sendAction(SearchDetailIntent.OnSearchQueryChanged(it)) },
            onEnter = { sendAction(SearchDetailIntent.RequestSearch(state.searchInput)) }
        )

        SearchResults(
            key = state.searchInput,
            searchResult = state.searchMusicResult,
            onClickSongItem = { onClickSongItem(it) }
        )
    }
}

@Composable
fun SearchWithSelectedSheet(
    state: SearchDetailUiState,
    paddingValues: PaddingValues,
    onClickSongItem: (SongUiData) -> Unit,
    sendAction: (SearchDetailIntent) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SearchContent(
            paddingValues = paddingValues,
            state = state,
            onClickSongItem = onClickSongItem,
            sendAction = sendAction,
        )

        if (state.selectedSongs.isNotEmpty()) {
            SelectedSongSheet(
                selectedSongs = state.selectedSongs.toList(),
                sendAction = sendAction,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
            )
        }
    }
}

@Composable
fun SearchBar(
    searchQuery: String,
    onQueryUpdate: (String) -> Unit,
    onEnter: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    // 화면 진입 시 자동 포커스 요청
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Box(modifier = Modifier.padding(vertical = 10.dp)) {
        WepliTextField(
            value = searchQuery,
            singleLine = true,
            onValueChanged = { onQueryUpdate(it) },
            onEnter = {
                onEnter()
                keyboardController?.hide()
            },
            placeholder = "검색어를 입력하세요.",
            type = WepliTextFieldType.Search,
            modifier = Modifier.focusRequester(focusRequester)
        )
    }
}

@Composable
fun SearchResults(
    key: String,
    searchResult: List<SongUiData>,
    onClickSongItem: (SongUiData) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    LaunchedEffect(key) {
        lazyListState.scrollToItem(0)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 24.dp)
    ) {
        items(count = searchResult.size, key = { searchResult[it].id }) { idx ->
            val song = searchResult[idx]

            SearchResultSongItem(
                songUiData = song,
                onClick = { onClickSongItem(song) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
            )
        }
    }
}

@Composable
fun SearchResultSongItem(
    modifier: Modifier = Modifier,
    songUiData: SongUiData,
    onClick: () -> Unit,
) {
    val imageSize = SongItemImageSize.toPx()
    val imageUrl = remember(songUiData.id) { songUiData.getImageUrl(imageSize) }
    val titleStyle = WepliTheme.typo.body4.run {
        if (songUiData.isSelected) {
            copy(brush = WepliTheme.color.linear3)
        } else {
            copy(color = WepliTheme.color.white)
        }
    }

    Row(modifier = modifier.clickable { onClick() }) {
        AsyncImageWithPreview(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(4.dp)),
            imageUrl = imageUrl,
            contentScale = ContentScale.Crop,
            imageOverrideSize = SongItemImageSize,
            loadingContent = {
                ShimmerSkeleton(
                    modifier = Modifier
                        .size(SongItemImageSize)
                        .clip(RoundedCornerShape(4.dp))
                )
            },
        )

        WepliSpacer(horizontal = 12.dp)
        Column(
            modifier = Modifier.fillMaxHeight().weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = songUiData.title,
                style = titleStyle,
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

        Icon(
            modifier = Modifier
                .padding(start = 4.dp)
                .align(Alignment.CenterVertically)
                .size(24.dp),
            tint = WepliTheme.color.gray800,
            painter = painterResource(id = R.drawable.ic_more_dot),
            contentDescription = null
        )
    }
}

@Composable
fun SelectedSongSheet(
    selectedSongs: List<SongUiData>,
    sendAction: (SearchDetailIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    val brush = WepliTheme.color.linear3
    var previousSize by remember { mutableIntStateOf(selectedSongs.size) }

    LaunchedEffect(selectedSongs.size) {
        if (selectedSongs.size > previousSize) {
            scrollState.scrollTo(scrollState.maxValue)
        }
        previousSize = selectedSongs.size
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .topBorderWithRoundedCorners(brush = brush, height = 1.dp, cornerRadius = 20.dp, alpha = 0.1f)
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(color = WepliTheme.color.black)
            .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .padding(vertical = 12.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            selectedSongs.forEach {
                // 캐싱된 이미지를 사용하기 위해 SongItem과 같은 Size의 이미지 url 사용
                SelectedSongItem(
                    title = it.title,
                    imageUrl = it.getImageUrl(SongItemImageSize.toPx()),
                    onClick = { sendAction(SearchDetailIntent.OnSongSelected(it)) }
                )
            }
        }

        WepliBasicButton(
            title = "${selectedSongs.size}곡 추가하기",
            isEnabled = true,
            onClick = { sendAction(SearchDetailIntent.OnCompleteSongSelect) },
            modifier = Modifier.padding(horizontal = 20.dp),
            buttonStyle = WepliButtonStyle.Basic,
        )
    }
}

@Composable
fun SelectedSongItem(
    title: String,
    imageUrl: String,
    onClick: () -> Unit,
) {
    val itemPadding = PaddingValues(top = 6.dp, bottom = 6.dp, start = 6.dp, end = 12.dp)
    val itemBrush = WepliTheme.color.linear3

    Box(
        modifier = Modifier
            .clickable { onClick() }
            .widthIn(max = 200.dp)
    ) {
        // 테투리 오버레이
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(100.dp))
                .matchParentSize()
                .alpha(0.5f)
                .border(brush = itemBrush, shape = CircleShape, width = 1.dp)
                .padding(itemPadding),
        )

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(100.dp))
                .background(brush = itemBrush, alpha = 0.1f)
                .padding(itemPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val selectedSongImageModifier = Modifier
                .size(20.dp)
                .clip(CircleShape)

            AsyncImageWithPreview(
                modifier = selectedSongImageModifier,
                imageUrl = imageUrl,
                contentScale = ContentScale.Crop,
                imageOverrideSize = 40.dp,
                loadingContent = {
                    ShimmerSkeleton(modifier = selectedSongImageModifier)
                },
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

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(SearchScreenMode.Normal, SearchDetailUiState(searchMusicResult = songMockData), {}, {})
}

@Preview
@Composable
fun SelectedSongSheetPreview() {
    SelectedSongSheet(songMockData, {}, Modifier.fillMaxWidth())
}