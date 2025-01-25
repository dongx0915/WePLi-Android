package com.wepli.community.write.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.WepliAppBar
import com.wepli.community.write.mvi.CommunityWriteUiState
import com.wepli.community.write.viewmodel.CommunityWriteViewModel
import org.orbitmvi.orbit.compose.collectAsState
import textfield.WepliTextField
import textfield.WepliTextFieldType
import theme.WepliTheme

@Composable
fun CommunityWriteScreenRoute() {
    val viewModel: CommunityWriteViewModel = hiltViewModel()
    val state: CommunityWriteUiState by viewModel.collectAsState()

    CommunityWriteScreen(
        title = state.title,
        contents = state.contents
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CommunityWriteScreen(
    title: String,
    contents: String
) {
    Scaffold(
        topBar = {
            WepliAppBar(
                title = "게시글 작성",
                showBackButton = true
            )
        },
        containerColor = WepliTheme.color.black
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 0.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            TitleLayout(title = "")

            ContentsLayout()
        }
    }
}

@Composable
fun TitleLayout(
    title: String,
    maxLength: Int = 50,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = "제목",
                style = WepliTheme.typo.body4,
                color = WepliTheme.color.gray900
            )
            Text(
                text = "*",
                style = WepliTheme.typo.body4.copy(
                    brush = WepliTheme.color.linear3
                ),
            )
        }

        WepliTextField(
            value = title,
            onValueChanged = { newValue ->
                if (newValue.length >= maxLength) {
                    // TODO: Show error message
                } else {
                    // TODO: Update Title
                }
            },
            singleLine = true,
            placeholder = "제목을 작성해주세요.",
            type = WepliTextFieldType.Normal
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "제목은 50자 이내로 작성해주세요.",
                style = WepliTheme.typo.body6,
                color = WepliTheme.color.gray500
            )

            Text(
                text = "${title.length}/$maxLength",
                style = WepliTheme.typo.body6,
                color = WepliTheme.color.gray500
            )
        }
    }
}

@Composable
fun ContentsLayout() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = "내용",
                style = WepliTheme.typo.body4,
                color = WepliTheme.color.gray900
            )
            Text(
                text = "*",
                style = WepliTheme.typo.body4.copy(
                    brush = WepliTheme.color.linear3
                ),
            )
        }

        WepliTextField(
            value = "",
            onValueChanged = { newValue ->

            },
            singleLine = false,
            placeholder = "내용을 작성해주세요.",
            type = WepliTextFieldType.MultiLine
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "내용은 500자 이내로 작성해주세요.",
                style = WepliTheme.typo.body6,
                color = WepliTheme.color.gray500
            )

            Text(
                text = "0/500",
                style = WepliTheme.typo.body6,
                color = WepliTheme.color.gray500
            )
        }
    }
}

@Preview
@Composable
fun CommunityWriteScreenPreview() {
    CommunityWriteScreen(
        title = "",
        contents = ""
    )
}