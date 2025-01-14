package com.wepli.search.detail

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.WepliAppBar
import com.wepli.search.detail.state.SearchDetailEffect
import com.wepli.search.detail.state.SearchDetailIntent
import com.wepli.search.detail.state.SearchDetailUiState
import com.wepli.search.detail.viewmodel.SearchDetailViewModel
import com.wepli.shared.feature.mock.songMockData
import com.wepli.uimodel.music.SongUiData
import common.WepliSpacer
import extensions.compose.shimmerEffect
import extensions.compose.toPx
import image.AsyncImageWithPreview
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import textfield.SearchMusicTextField
import theme.WepliTheme

@Composable
fun SearchScreenRoute() {
    val viewModel = hiltViewModel<SearchDetailViewModel>()
    val state: SearchDetailUiState by viewModel.collectAsState()
    val scrollState = rememberLazyListState()
    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SearchDetailEffect.SearchError -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    SearchScreen(
        onQueryUpdate = { viewModel.processIntent(SearchDetailIntent.OnSearchQueryChanged(it)) },
        onEnter = { viewModel.processIntent(SearchDetailIntent.RequestSearch(state.searchInput)) },
        searchQuery = state.searchInput,
        searchResult = state.searchMusicResult,
        lazyListState = scrollState
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onQueryUpdate: (String) -> Unit,
    onEnter: () -> Unit,
    searchQuery: String,
    searchResult: List<SongUiData>,
    lazyListState: LazyListState,
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
                title = "곡 검색"
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            Box(modifier = Modifier.padding(vertical = 10.dp)) {
                SearchMusicTextField(
                    query = searchQuery,
                    onQueryUpdate = { onQueryUpdate(it) },
                    onEnter = { onEnter() },
                    placeholderText = "검색어를 입력하세요.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item { WepliSpacer(vertical = 12.dp) }

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
    SearchScreen({}, {}, "", songMockData, rememberLazyListState())
}