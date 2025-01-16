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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import appbar.WepliAppBar
import com.wepli.shared.feature.mock.keywordMockData
import common.WepliSpacer
import compose.HighlightedText
import model.recommend.RecommendKeyword
import org.joda.time.LocalDate
import textfield.SearchMusicTextField
import theme.WepliTheme

@Composable
fun SearchMainScreenRoute(
    navOnSearchDetail: (searchQuery: String) -> Unit
) {
    val keywordMockData = keywordMockData.random()

    SearchMainScreen(
        recommendKeyword = keywordMockData,
        navOnSearchDetail = navOnSearchDetail
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableInteractionSource")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMainScreen(
    recommendKeyword: RecommendKeyword,
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
            Box(modifier = Modifier.padding(vertical = 10.dp)) {
                SearchMusicTextField(
                    query = "",
                    readOnly = true,
                    onQueryUpdate = {},
                    onEnter = {},
                    placeholderText = "검색어를 입력하세요.",
                    modifier = Modifier
                        .focusRequester(FocusRequester())
                        .onFocusChanged {
                            if (it.isFocused) {
                                navOnSearchDetail("")
                            }
                        }
                        .fillMaxWidth()
                        .height(44.dp),
                )
            }

            WepliSpacer(vertical = 36.dp)

            RecommendKeywordsLayout(recommendKeyword, navOnSearchDetail)

            WepliSpacer(vertical = 40.dp)

            HotSearchKeywordLayout()
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
    val textStyle = if (isHighlightTag) {
        WepliTheme.typo.body1.copy(
            brush = WepliTheme.color.linear3,
            fontStyle = FontStyle.Italic,
        )
    } else {
        WepliTheme.typo.body1.copy(
            color = WepliTheme.color.gray900,
            fontStyle = FontStyle.Normal,
        )
    }

    Box(
        modifier = modifier
            .background(
                color = WepliTheme.color.gray050,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(
            text = keyword,
            style = textStyle,
        )
    }
}

@Composable
fun HotSearchKeywordLayout() {
    val today = LocalDate.now()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
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
            repeat(10) {
                HotSearchKeyword(it + 1, "검색어")
            }
        }
    }
}

@Composable
fun HotSearchKeyword(
    rank: Int,
    keyword: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.width(20.dp),
            text = rank.toString(),
            style = WepliTheme.typo.body2.copy(
                brush = WepliTheme.color.linear3,
            )
        )
        WepliSpacer(horizontal = 8.dp)
        Text(
            text = keyword,
            style = WepliTheme.typo.body2,
            color = WepliTheme.color.gray900
        )
    }
}

@Preview
@Composable
fun SearchMainScreenPreview() {
    SearchMainScreen(
        recommendKeyword = keywordMockData.random(),
        navOnSearchDetail = {}
    )
}