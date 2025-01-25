package textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wepli.designsystem.R
import theme.WepliTheme

sealed interface WepliTextFieldType {

    @Composable
    fun leadingIcon(): Painter?

    @Composable
    fun trailingIcon(): Painter?

    fun minHeight(): Dp = 44.dp
    fun maxHeight(): Dp = 44.dp

    fun isEnabled(): Boolean = true

    data object Normal : WepliTextFieldType {
        @Composable
        override fun leadingIcon(): Painter? = null

        @Composable
        override fun trailingIcon(): Painter? = null
    }

    data object Search : WepliTextFieldType {
        @Composable
        override fun leadingIcon(): Painter = painterResource(id = R.drawable.ic_search)

        @Composable
        override fun trailingIcon(): Painter? = null
    }

    data object SearchTwo : WepliTextFieldType {
        @Composable
        override fun leadingIcon(): Painter? = null

        @Composable
        override fun trailingIcon(): Painter = painterResource(id = R.drawable.ic_search)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WepliTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    isError: Boolean = false,
    singleLine: Boolean = false,
    readOnly: Boolean = false,
    onValueChanged: (String) -> Unit = { _ -> },
    onEnter: () -> Unit = {},
    onFocusChanged: (FocusState) -> Unit = {},
    focusRequester: FocusRequester = remember { FocusRequester() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(onDone = { onEnter() }),
    type: WepliTextFieldType,
) {
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
    val interactionSource = remember { MutableInteractionSource() }
    val customTextSelectionColors = TextSelectionColors(
        handleColor = WepliTheme.color.gray700,
        backgroundColor = WepliTheme.color.gray300,
    )
    val textFieldColors = OutlinedTextFieldDefaults.colors(
        // TextField 색상
        focusedContainerColor = WepliTheme.color.gray000,
        unfocusedContainerColor = WepliTheme.color.gray000,

        focusedBorderColor = WepliTheme.color.gray000,
        unfocusedBorderColor = WepliTheme.color.gray000,

        unfocusedTextColor = WepliTheme.color.gray900,
        focusedTextColor = WepliTheme.color.gray900,
        selectionColors = TextSelectionColors(
            handleColor = WepliTheme.color.gray900,
            backgroundColor = WepliTheme.color.gray300,
        ),
    )

    LaunchedEffect(value) {
        if (textFieldValueState.text != value) {
            textFieldValueState = textFieldValueState.copy(text = value)
        }
    }

    Box(modifier = Modifier.wrapContentSize()) {
        CompositionLocalProvider(
            LocalTextSelectionColors provides customTextSelectionColors
        ) {
            BasicTextField(
                value = textFieldValueState,
                modifier = modifier
                    .heightIn(
                        min = type.minHeight(),
                        max = type.maxHeight(),
                    )
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState -> onFocusChanged(focusState) },
                onValueChange = { newValue ->
                    textFieldValueState = newValue
                    onValueChanged(newValue.text)
                },
                enabled = type.isEnabled(),
                readOnly = readOnly,
                textStyle = WepliTheme.typo.body2.copy(
                    color = WepliTheme.color.gray900,
                ),
                cursorBrush = SolidColor(WepliTheme.color.gray900),
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
                                style = WepliTheme.typo.body2,
                                color = WepliTheme.color.gray500,
                            )
                        },
                        leadingIcon = type.leadingIcon()?.let { // 텍스트 앞에 보여줄 아이콘
                            {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    painter = it,
                                    tint = WepliTheme.color.gray300,
                                    contentDescription = null
                                )
                            }
                        },
                        trailingIcon = type.trailingIcon()?.let { // 텍스트 끝에 보여줄 아이콘
                            {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    painter = it,
                                    tint = WepliTheme.color.gray300,
                                    contentDescription = null
                                )
                            }
                        },
                        singleLine = singleLine,
                        enabled = type.isEnabled(),
                        isError = isError,
                        interactionSource = interactionSource,
                        colors = textFieldColors,
                        contentPadding = if (singleLine) {
                            PaddingValues(vertical = 0.dp, horizontal = 16.dp)
                        } else {
                            contentPadding()
                        },
                        container = {
                            Container(
                                enabled = type.isEnabled(),
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
}

@Preview
@Composable
private fun WepliTextFieldPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        WepliTextField(
            value = "",
            onValueChanged = { _ -> },
            onEnter = {},
            onFocusChanged = {},
            singleLine = true,
            placeholder = "Placeholder",
            type = WepliTextFieldType.Normal,
        )

        WepliTextField(
            value = "",
            onValueChanged = { _ -> },
            onEnter = {},
            onFocusChanged = {},
            singleLine = true,
            placeholder = "검색어를 입력하세요.",
            type = WepliTextFieldType.Search,
        )

        WepliTextField(
            value = "",
            onValueChanged = { _ -> },
            onEnter = {},
            onFocusChanged = {},
            singleLine = true,
            placeholder = "검색어를 입력하세요.",
            type = WepliTextFieldType.SearchTwo,
        )
    }
}