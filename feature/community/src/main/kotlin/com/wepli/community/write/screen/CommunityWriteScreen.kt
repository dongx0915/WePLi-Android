package com.wepli.community.write.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.WepliAppBar
import com.wepli.community.write.mvi.CommunityWriteIntent
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
        state = state,
        sendAction = viewModel::processIntent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CommunityWriteScreen(
    state: CommunityWriteUiState,
    sendAction: (CommunityWriteIntent) -> Unit,
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
            TitleLayout(
                title = state.title.text,
                isTitleLengthExceeded = state.title.isLengthExceeded,
                sendAction = sendAction
            )

            ContentsLayout(
                contents = state.contents.text,
                isContentsLengthExceeded = state.contents.isLengthExceeded,
                sendAction = sendAction
            )

            AddSongLayout()
        }
    }
}

@Composable
fun TitleLayout(
    title: String,
    maxLength: Int = 25,
    isTitleLengthExceeded: Boolean,
    sendAction: (CommunityWriteIntent) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FieldLabel("제목", "*", true)
        LimitedLengthTextField(
            value = title,
            maxLength = maxLength,
            isLengthExceeded = isTitleLengthExceeded,
            placeholder = "제목을 작성해주세요.",
            errorText = "제목은 ${maxLength}자 이내로 작성해주세요.",
            type = WepliTextFieldType.Normal,
            onValueChanged = { newValue, maxLength ->
                sendAction(CommunityWriteIntent.UpdateTitle(newValue, maxLength))
            }
        )
    }
}

@Composable
fun ContentsLayout(
    contents: String,
    maxLength: Int = 250,
    isContentsLengthExceeded: Boolean,
    sendAction: (CommunityWriteIntent) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FieldLabel("내용", "*", true)

        LimitedLengthTextField(
            value = contents,
            maxLength = maxLength,
            isLengthExceeded = isContentsLengthExceeded,
            placeholder = "내용을 작성해주세요.",
            errorText = "내용은 ${maxLength}자 이내로 작성해주세요.",
            type = WepliTextFieldType.MultiLine,
            onValueChanged = { newValue, maxLength ->
                sendAction(CommunityWriteIntent.UpdateContents(newValue, maxLength))
            }
        )
    }
}

@Composable
fun AddSongLayout() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FieldLabel("노래 추가하기", "선택", false)
    }
}

@Composable
fun FieldLabel(
    text: String,
    label: String,
    isRequired: Boolean,
) {
    val labelStyle = with(WepliTheme.typo) {
        if (isRequired) body4 else caption2
    }

    Row(
        verticalAlignment = if (isRequired) Alignment.Top else Alignment.CenterVertically,
        horizontalArrangement = if (isRequired) Arrangement.spacedBy(2.dp) else Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = text,
            style = WepliTheme.typo.body4,
            color = WepliTheme.color.gray900
        )
        Text(
            text = label,
            style = labelStyle.copy(
                brush = WepliTheme.color.linear3
            )
        )
    }
}

@Composable
fun LimitedLengthTextField(
    value: String,
    maxLength: Int,
    isLengthExceeded: Boolean,
    placeholder: String,
    errorText: String,
    type: WepliTextFieldType,
    onValueChanged: (String, Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        WepliTextField(
            value = value,
            onValueChanged = { newValue ->
                onValueChanged(newValue, maxLength)
            },
            isError = isLengthExceeded,
            singleLine = type == WepliTextFieldType.Normal,
            placeholder = placeholder,
            type = type
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (isLengthExceeded) {
                Text(
                    text = errorText,
                    style = WepliTheme.typo.body6,
                    color = WepliTheme.color.red500
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${value.length}/$maxLength",
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
        state = CommunityWriteUiState(),
        sendAction = {}
    )
}