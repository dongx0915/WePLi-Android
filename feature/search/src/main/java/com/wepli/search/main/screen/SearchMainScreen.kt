package com.wepli.search.main.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import appbar.WepliAppBar
import com.wepli.shared.feature.mock.keywordMockData
import common.WepliSpacer
import compose.HighlightedText
import model.recommend.RecommendKeyword
import textfield.SearchMusicTextField
import theme.WepliTheme

@Composable
fun SearchMainScreenRoute() {
    val keywordMockData = keywordMockData.random()

    SearchMainScreen(
        recommendKeyword = keywordMockData
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMainScreen(
    recommendKeyword: RecommendKeyword
) {
    Scaffold(
        containerColor = WepliTheme.color.black,
        topBar = {
            WepliAppBar(
                showLogo = false,
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
                    query = "",
                    readOnly = true,
                    onQueryUpdate = {},
                    onEnter = {},
                    placeholderText = "검색어를 입력하세요.",
                    modifier = Modifier
                        .focusRequester(FocusRequester())
                        .onFocusChanged {
                            if (it.isFocused) {
                                navOnSearchDetail()
                            }
                        }
                        .fillMaxWidth()
                        .height(44.dp),
                )
            }

            WepliSpacer(vertical = 36.dp)

            RecommendKeywordsLayout(recommendKeyword)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecommendKeywordsLayout(
    recommendKeyword: RecommendKeyword
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
                KeywordComponent(keyword = keyword.text, isHighlightTag = keyword.isHighlightTag)
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
}