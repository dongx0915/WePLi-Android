package com.wepli.search.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.WepliAppBar
import com.wepli.search.state.SearchIntent
import com.wepli.search.state.SearchUiState
import com.wepli.search.viewmodel.SearchViewModel
import org.orbitmvi.orbit.compose.collectAsState
import theme.WepliTheme

@Composable
fun SearchScreenRoute() {
    val viewModel = hiltViewModel<SearchViewModel>()
    val state: SearchUiState by viewModel.collectAsState()

    SearchScreen(
        viewModel = viewModel,
        searchQuery = state.searchInput,
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    searchQuery: String,
) {
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
                    onQueryUpdate = { viewModel.processIntent(SearchIntent.OnSearchQueryChanged(it)) },
                    onEnter = { viewModel.processIntent(SearchIntent.RequestSearch(searchQuery)) },
                    placeholderText = "검색어를 입력하세요.",
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                )
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

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(hiltViewModel(), "")
}