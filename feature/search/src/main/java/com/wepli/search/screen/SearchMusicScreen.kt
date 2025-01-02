package com.wepli.search.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.WepliAppBar
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.wepli.search.state.SearchEffect
import com.wepli.search.state.SearchIntent
import com.wepli.search.state.SearchUiState
import com.wepli.search.viewmodel.SearchViewModel
import com.wepli.shared.feature.mock.songMockData
import com.wepli.uimodel.music.SongUiData
import common.WepliSpacer
import extensions.compose.shimmerEffect
import extensions.compose.toPx
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import theme.WepliTheme

@Composable
fun SearchScreenRoute() {
    val viewModel = hiltViewModel<SearchViewModel>()
    val state: SearchUiState by viewModel.collectAsState()
    val scrollState = rememberLazyListState()
    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SearchEffect.SearchError -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    SearchScreen(
        onQueryUpdate = { viewModel.processIntent(SearchIntent.OnSearchQueryChanged(it)) },
        onEnter = { viewModel.processIntent(SearchIntent.RequestSearch(state.searchInput)) },
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
                WepliTextField(
                    query = searchQuery,
                    onQueryUpdate = { onQueryUpdate(it) },
                    onEnter = { onEnter() },
                    placeholderText = "검색어를 입력하세요.",
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
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

                items(searchResult.size) { idx ->
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

// TODO 공통 TextField로 추출 필요
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WepliTextField(
    modifier: Modifier,
    query: String,
    onQueryUpdate: (String) -> Unit,
    onEnter: () -> Unit,
    placeholderText: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    keyboardActions: KeyboardActions = KeyboardActions(onDone = { onEnter() }),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentPadding: PaddingValues = PaddingValues(all = 0.dp),
) {
    BasicTextField(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(WepliTheme.color.gray000),
        value = query,
        onValueChange = { onQueryUpdate(it) },
        textStyle = WepliTheme.typo.body2.copy(
            color = WepliTheme.color.gray900,
        ),
        cursorBrush = SolidColor(WepliTheme.color.gray900),
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = query,
                innerTextField = innerTextField,
                enabled = true, // TextField 활성화/비활성화
                singleLine = true, // TextField 한 줄로 표시
                visualTransformation = VisualTransformation.None, // 텍스트 타입 (비밀번호, 전화번호 등)
                placeholder = {
                    if (query.isEmpty()) {
                        Text(
                            text = placeholderText,
                            style = WepliTheme.typo.body2,
                            color = WepliTheme.color.gray500,
                        )
                    }
                }, // PlaceHolder (별도 Composable로 선언)
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = com.wepli.designsystem.R.drawable.ic_search),
                        tint = WepliTheme.color.gray300,
                        contentDescription = null
                    )
                }, // 텍스트 앞에 보여줄 아이콘 (별도 Composable로 선언)
                trailingIcon = null, // 텍스트 끝에 보여줄 아이콘
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = WepliTheme.color.gray900,
                    focusedTextColor = WepliTheme.color.gray900,
                ), // TextField 색상
                interactionSource = interactionSource, // ??
                isError = false,
                contentPadding = contentPadding,
                container = {},
            )
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}

@Composable
fun SongItem(
    modifier: Modifier = Modifier,
    songUiData: SongUiData,
) {
    val context = LocalContext.current
    val imageUrl = songUiData.getImageUrl(52.dp.toPx())
    val imageSize = 52.dp.toPx()
    val imageRequest = remember(imageUrl) {
        ImageRequest.Builder(context).apply {
            data(imageUrl)
            size(imageSize)
            crossfade(true)
        }.build()
    }

    Row(modifier = modifier) {
        SubcomposeAsyncImage(
            model = imageRequest,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Fit,
            contentDescription = null,
            loading = { SkeletonImage() },
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