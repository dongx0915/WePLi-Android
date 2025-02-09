package com.wepli.feature.namecard.detail.chapter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import button.WepliBasicButton
import button.WepliButtonStyle
import com.wepli.feature.namecard.detail.mvi.NameCardDetailUiState
import textfield.LimitedLengthTextField
import textfield.WepliTextField
import textfield.WepliTextFieldType
import theme.WepliTheme

@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun NameCardChapterThreeScreenPreview() {
    NameCardChapterThreeScreen(
        state = NameCardDetailUiState(),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(top = 56.dp, bottom = 20.dp)
    )
}

@Composable
fun NameCardChapterThreeScreen(
    state: NameCardDetailUiState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Instagram 계정을\n입력해주세요",
            style = WepliTheme.typo.title1,
            color = WepliTheme.color.gray900
        )
        Spacer(modifier = Modifier.padding(top = 12.dp))
        Text(
            text = "명함에 표시될 SNS 계정을 입력해주세요\n계정 입력을 원하지 않으면 건너뛸 수 있어요",
            style = WepliTheme.typo.body4,
            color = WepliTheme.color.gray500
        )

        Spacer(modifier = Modifier.height(32.dp))
        WepliTextField(
            value = "",
            onValueChanged = { newValue ->

            },
            singleLine = true,
            placeholder = "계정을 입력해주세요",
            type = WepliTextFieldType.Normal,
        )

        Spacer(modifier = Modifier.weight(1f))
        WepliBasicButton(
            title = "입력완료",
            isEnabled = true,
            onClick = { },
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            buttonStyle = WepliButtonStyle.Basic,
        )
        Spacer(modifier = Modifier.height(8.dp))
        WepliBasicButton(
            title = "건너뛰기",
            isEnabled = true,
            onClick = { },
            modifier = Modifier
                .padding(bottom = 20.dp)
                .align(Alignment.CenterHorizontally),
            buttonStyle = WepliButtonStyle.Transparent,
        )
    }
}