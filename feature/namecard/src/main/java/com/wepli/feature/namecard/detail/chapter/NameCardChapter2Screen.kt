package com.wepli.feature.namecard.detail.chapter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import button.WepliBasicButton
import button.WepliButtonStyle
import com.wepli.feature.namecard.detail.mvi.NameCardDetailIntent
import com.wepli.feature.namecard.detail.mvi.NameCardDetailUiState
import textfield.LimitedLengthTextField
import textfield.WepliTextFieldType
import theme.WepliTheme

@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun NameCardChapter2ScreenPreview() {
    NameCardChapter2Screen(
        state = NameCardDetailUiState(),
        sendAction = {},
        navOnNextPage = {},
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 56.dp, bottom = 20.dp)
    )
}

@Composable
fun NameCardChapter2Screen(
    state: NameCardDetailUiState,
    sendAction: (NameCardDetailIntent) -> Unit,
    navOnNextPage: () -> Unit,
    modifier: Modifier = Modifier
) {
    val oneLineIntro = state.oneLineIntro
    val isEnabled by remember(oneLineIntro.text) {
        derivedStateOf { oneLineIntro.text.isNotEmpty() && oneLineIntro.isLengthExceed.not() }
    }

    Column(modifier = modifier.padding(horizontal = 24.dp)) {
        Text(
            text = "한 줄 소개를 입력해주세요",
            style = WepliTheme.typo.title1,
            color = WepliTheme.color.gray900
        )
        Spacer(modifier = Modifier.padding(top = 12.dp))
        Text(
            text = "oo님을 잘 나타내는 문장을 입력해주세요",
            style = WepliTheme.typo.body4,
            color = WepliTheme.color.gray500
        )

        Spacer(modifier = Modifier.height(32.dp))
        LimitedLengthTextField(
            value = oneLineIntro.text,
            onValueChanged = { newValue, _ ->
                sendAction(NameCardDetailIntent.OnChangedOneLineIntro(newValue))
            },
            placeholder = "문구를 작성해주세요",
            errorText = "최대 ${oneLineIntro.maxLength}자까지 입력 가능합니다",
            maxLength = oneLineIntro.maxLength,
            isLengthExceeded = oneLineIntro.isLengthExceed,
            type = WepliTextFieldType.Normal,
        )

        Spacer(modifier = Modifier.weight(1f))
        WepliBasicButton(
            title = "입력완료",
            isEnabled = isEnabled,
            onClick = { navOnNextPage() },
            modifier = Modifier
                .padding(bottom = 20.dp)
                .align(Alignment.CenterHorizontally),
            buttonStyle = WepliButtonStyle.Basic,
        )
    }
}