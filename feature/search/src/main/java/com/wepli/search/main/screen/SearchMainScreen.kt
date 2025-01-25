package com.wepli.search.main.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.WepliAppBar
import com.wepli.search.main.mvi.SearchMainUiState
import com.wepli.search.main.viewmodel.SearchMainViewModel
import com.wepli.shared.feature.mock.keywordMockData
import com.wepli.shared.feature.mock.songMockData
import common.WepliSpacer
import compose.HighlightedText
import model.recommend.RecommendKeyword
import org.joda.time.LocalDate
import org.orbitmvi.orbit.compose.collectAsState
import textfield.SearchMusicTextField
import textfield.WepliTextField
import textfield.WepliTextFieldType
import theme.WepliTheme

@Composable
fun SearchMainScreenRoute(
    navOnSearchDetail: (searchQuery: String) -> Unit
) {
    val viewModel: SearchMainViewModel = hiltViewModel()
    val state: SearchMainUiState by viewModel.collectAsState()

    SearchMainScreen(
        recommendKeyword = state.recommendKeyword,
        hotKeywords = state.hotKeywords,
        navOnSearchDetail = navOnSearchDetail
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableInteractionSource")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMainScreen(
    recommendKeyword: RecommendKeyword?,
    hotKeywords: List<String>,
    navOnSearchDetail: (searchQuery: String) -> Unit
) {
    val scrollState: ScrollState = rememberScrollState()

    Scaffold(
        containerColor = WepliTheme.color.black,
        topBar = {
            WepliAppBar(
                showLogo = false,
                title = "곡 검색"
            )
        }
    ) { paddingValues ->
        val (topPadding, bottomPadding) = paddingValues.calculateTopPadding() to paddingValues.calculateBottomPadding() + 56.dp

        Column(
            modifier = Modifier
                .padding(top = topPadding, bottom = bottomPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(scrollState)
        ) {
            Box(modifier = Modifier.padding(top = 10.dp)) {
                WepliTextField(
                    value = "",
                    readOnly = true,
                    singleLine = true,
                    onFocusChanged = {
                        if (it.isFocused) {
                            navOnSearchDetail("")
                        }
                    },
                    placeholder = "검색어를 입력하세요.",
                    type = WepliTextFieldType.Search
                )
            }

            if (recommendKeyword != null) {
                WepliSpacer(vertical = 40.dp)
                RecommendKeywordsLayout(recommendKeyword, navOnSearchDetail)
            }

            WepliSpacer(vertical = 40.dp)
            HotSearchKeywordLayout(hotKeywords, navOnSearchDetail)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecommendKeywordsLayout(
    recommendKeyword: RecommendKeyword,
    navOnSearchDetail: (searchQuery: String) -> Unit,
) {
    Column {
        HighlightedText(
            text = recommendKeyword.subject,
            delimiter = "**",
            textStyle = WepliTheme.typo.title3.copy(
                color = WepliTheme.color.gray900,
                lineHeight = 28.sp
            ),
            highlightColor = WepliTheme.color.linear3
        )

        WepliSpacer(vertical = 16.dp)

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            maxLines = 3,
        ) {
            recommendKeyword.keywords.forEach { keyword ->
                KeywordComponent(
                    modifier = Modifier.clickable { navOnSearchDetail(keyword.text) },
                    keyword = keyword.text, isHighlightTag = keyword.isHighlightTag
                )
            }
        }
    }
}

@Composable
fun KeywordComponent(
    modifier: Modifier = Modifier,
    keyword: String,
    isHighlightTag: Boolean
) {
    val textStyle = WepliTheme.typo.body1.run {
        if (isHighlightTag) {
            copy(brush = WepliTheme.color.linear3,)
        } else {
            copy(color = WepliTheme.color.gray900,)
        }
    }

    Box(
        modifier = modifier
            .background(
                color = WepliTheme.color.gray050,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = 6.dp, horizontal = 16.dp)
    ) {
        Text(
            text = keyword,
            style = textStyle,
        )
    }
}

@Composable
fun HotSearchKeywordLayout(
    hotKeywords: List<String>,
    navOnSearchDetail: (searchQuery: String) -> Unit,
) {
    val today = LocalDate.now()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "인기 검색어",
                style = WepliTheme.typo.subTitle1,
                color = WepliTheme.color.gray900
            )

            Text(
                text = "$today 기준",
                style = WepliTheme.typo.body6,
                color = WepliTheme.color.gray700
            )
        }

        Column(
            modifier = Modifier.padding(bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            hotKeywords.forEachIndexed { index, keyword ->
                HotSearchKeyword(
                    modifier = Modifier.clickable { navOnSearchDetail(keyword) },
                    rank = index + 1,
                    keyword = keyword
                )
            }
        }
    }
}

@Composable
fun HotSearchKeyword(
    modifier: Modifier = Modifier,
    rank: Int,
    keyword: String,
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.width(20.dp),
            text = rank.toString(),
            style = WepliTheme.typo.body2.copy(
                brush = WepliTheme.color.linear3,
            )
        )
        WepliSpacer(horizontal = 4.dp)
        Text(
            text = keyword,
            style = WepliTheme.typo.body4,
            color = WepliTheme.color.gray900,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun SearchMainScreenPreview() {
    SearchMainScreen(
        recommendKeyword = keywordMockData.random(),
        hotKeywords = songMockData.take(10).map { it.title },
        navOnSearchDetail = {}
    )
}