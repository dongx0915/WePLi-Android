package com.wepli.devmode.fcm.presentation.textfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults.Container
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wepli.devmode.fcm.presentation.theme.DevModeTheme

sealed class DevModeTextFieldType(
    val isEnabled: Boolean = true,
) {
    @Composable
    open fun leadingIcon(): Painter? = null

    @Composable
    open fun trailingIcon(): Painter? = null

    data object Normal : DevModeTextFieldType()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DevModeTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    isError: Boolean = false,
    singleLine: Boolean = false,
    readOnly: Boolean = false,
    onValueChanged: (String) -> Unit = { _ -> },
    onEnter: () -> Unit = {},
    onFocusChanged: (FocusState) -> Unit = {},
    onClickLeadingIcon: () -> Unit = {},
    onClickTrailingIcon: () -> Unit = {},
    focusRequester: FocusRequester = remember { FocusRequester() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(onDone = { onEnter() }),
    type: DevModeTextFieldType,
) {
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
    val interactionSource = remember { MutableInteractionSource() }
    val customTextSelectionColors = TextSelectionColors(
        handleColor = DevModeTheme.color.gray700,
        backgroundColor = DevModeTheme.color.gray300,
    )
    val textFieldColors = OutlinedTextFieldDefaults.colors(
        // 배경색
        focusedContainerColor = DevModeTheme.color.gray000,
        unfocusedContainerColor = DevModeTheme.color.gray000,
        // 테두리 색
        focusedBorderColor = DevModeTheme.color.gray000,
        unfocusedBorderColor = DevModeTheme.color.gray000,
        // 텍스트 색상
        unfocusedTextColor = DevModeTheme.color.gray900,
        focusedTextColor = DevModeTheme.color.gray900,
        selectionColors = TextSelectionColors(
            handleColor = DevModeTheme.color.gray900,
            backgroundColor = DevModeTheme.color.gray300,
        ),
        // 에러 색상
        errorBorderColor = DevModeTheme.color.red500,
        errorContainerColor = DevModeTheme.color.gray000,
    )

    LaunchedEffect(value) {
        if (textFieldValueState.text != value) {
            textFieldValueState = textFieldValueState.copy(text = value)
        }
    }

    CompositionLocalProvider(
        LocalTextSelectionColors provides customTextSelectionColors
    ) {
        BasicTextField(
            value = textFieldValueState,
            modifier = modifier
                .fillMaxSize()
                .focusRequester(focusRequester)
                .onFocusChanged { focusState -> onFocusChanged(focusState) },
            onValueChange = { newValue ->
                textFieldValueState = newValue
                onValueChanged(newValue.text)
            },
            enabled = type.isEnabled,
            readOnly = readOnly,
            textStyle = DevModeTheme.typo.body2.copy(
                color = DevModeTheme.color.gray900,
            ),
            cursorBrush = SolidColor(DevModeTheme.color.gray900),
            visualTransformation = VisualTransformation.None, // 텍스트 타입 (비밀번호, 전화번호 등)
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            singleLine = singleLine,
            decorationBox = @Composable { innerTextField ->
                OutlinedTextFieldDefaults.DecorationBox(
                    value = textFieldValueState.text,
                    visualTransformation = VisualTransformation.None,
                    innerTextField = innerTextField,
                    placeholder = {
                        Text(
                            text = placeholder,
                            style = DevModeTheme.typo.body2,
                            color = DevModeTheme.color.gray500,
                        )
                    },
                    leadingIcon = type.leadingIcon()?.let { // 텍스트 앞에 보여줄 아이콘
                        {
                            Icon(
                                modifier = Modifier.size(20.dp).clickable { onClickLeadingIcon() },
                                painter = it,
                                tint = DevModeTheme.color.gray300,
                                contentDescription = null
                            )
                        }
                    },
                    trailingIcon = type.trailingIcon()?.let { // 텍스트 끝에 보여줄 아이콘
                        {
                            Icon(
                                modifier = Modifier.size(20.dp).clickable { onClickTrailingIcon() },
                                painter = it,
                                tint = DevModeTheme.color.gray300,
                                contentDescription = null
                            )
                        }
                    },
                    singleLine = singleLine,
                    enabled = type.isEnabled,
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = textFieldColors,
                    contentPadding = if (singleLine) {
                        PaddingValues(vertical = 0.dp, horizontal = 16.dp)
                    } else {
                        PaddingValues(vertical = 12.dp, horizontal = 16.dp)
                    },
                    container = {
                        Container(
                            enabled = type.isEnabled,
                            isError = isError,
                            interactionSource = interactionSource,
                            colors = textFieldColors,
                            shape = RoundedCornerShape(4.dp),
                            focusedBorderThickness = 0.dp,
                        )
                    },
                )
            }
        )
    }
}

@Composable
fun LimitedLengthTextField(
    value: String,
    singleLine: Boolean,
    maxLength: Int,
    isLengthExceeded: Boolean,
    placeholder: String,
    errorText: String,
    type: DevModeTextFieldType,
    onValueChanged: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DevModeTextField(
            value = value,
            onValueChanged = { newValue ->
                onValueChanged(newValue, maxLength)
            },
            isError = isLengthExceeded,
            singleLine = singleLine,
            placeholder = placeholder,
            type = type,
            modifier = textFieldModifier
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (isLengthExceeded) {
                Text(
                    text = errorText,
                    style = DevModeTheme.typo.body6,
                    color = DevModeTheme.color.red500
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${value.length}/$maxLength",
                style = DevModeTheme.typo.body6,
                color = DevModeTheme.color.gray500
            )
        }
    }
}

@Preview
@Composable
private fun DevModeTextFieldPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DevModeTextField(
            value = "",
            onValueChanged = { _ -> },
            onEnter = {},
            onFocusChanged = {},
            singleLine = true,
            placeholder = "Placeholder",
            type = DevModeTextFieldType.Normal,
            modifier = Modifier.height(44.dp)
        )

        DevModeTextField(
            value = "",
            onValueChanged = { _ -> },
            onEnter = {},
            onFocusChanged = {},
            singleLine = false,
            placeholder = "Placeholder",
            type = DevModeTextFieldType.Normal,
            modifier = Modifier.wrapContentHeight().heightIn(min = 44.dp, max = 100.dp)
        )

        DevModeTextField(
            value = "에러 발생",
            onValueChanged = { _ -> },
            onEnter = {},
            onFocusChanged = {},
            isError = true,
            singleLine = true,
            placeholder = "Placeholder",
            type = DevModeTextFieldType.Normal,
            modifier = Modifier.height(44.dp)
        )

        LimitedLengthTextField(
            value = "",
            maxLength = 100,
            isLengthExceeded = false,
            placeholder = "제목을 작성해주세요.",
            errorText = "최대 100자까지 입력 가능합니다.",
            singleLine = true,
            type = DevModeTextFieldType.Normal,
            onValueChanged = { newValue, maxLength ->  },
            textFieldModifier = Modifier.height(44.dp)
        )
    }
}