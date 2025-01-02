package textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import theme.WepliTheme

// TODO 공통 TextField로 추출 필요
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMusicTextField(
    modifier: Modifier,
    query: String,
    onQueryUpdate: (String) -> Unit,
    onEnter: () -> Unit,
    placeholderText: String = "",
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
        cursorBrush = SolidColor(WepliTheme.color.gray900),
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = query,
                innerTextField = innerTextField,
                enabled = true, // TextField 활성화/비활성화
                singleLine = true, // TextField 한 줄로 표시
                visualTransformation = VisualTransformation.None, // 텍스트 타입 (비밀번호, 전화번호 등)
                placeholder = { // PlaceHolder (별도 Composable로 선언)
                    if (query.isEmpty()) {
                        Text(
                            text = placeholderText,
                            style = WepliTheme.typo.body2,
                            color = WepliTheme.color.gray500,
                        )
                    }
                },
                leadingIcon = { // 텍스트 앞에 보여줄 아이콘 (별도 Composable로 선언)
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = com.wepli.designsystem.R.drawable.ic_search),
                        tint = WepliTheme.color.gray300,
                        contentDescription = null
                    )
                },
                trailingIcon = null, // 텍스트 끝에 보여줄 아이콘
                colors = TextFieldDefaults.colors( // TextField 색상
                    unfocusedTextColor = WepliTheme.color.gray900,
                    focusedTextColor = WepliTheme.color.gray900,
                ),
                interactionSource = remember { MutableInteractionSource() }, // ??
                isError = false,
                contentPadding = PaddingValues(all = 0.dp),
                container = {},
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { onEnter() }),
    )
}
