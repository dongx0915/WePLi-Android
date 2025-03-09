package button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import theme.WepliTheme

sealed interface WepliButtonStyle {
    @Composable
    fun colors(): ButtonColors

    data object Basic : WepliButtonStyle {
        @Composable
        override fun colors(): ButtonColors {
            return ButtonDefaults.buttonColors(
                containerColor = WepliTheme.color.gray000,
                contentColor = WepliTheme.color.gray900,
                disabledContainerColor = WepliTheme.color.gray050,
                disabledContentColor = WepliTheme.color.gray400,
            )
        }
    }

    data class Transparent(val alpha: Float = 0.0f) : WepliButtonStyle {
        @Composable
        override fun colors(): ButtonColors {
            return ButtonDefaults.buttonColors(
                containerColor = Color.White.copy(alpha = alpha),
                contentColor = WepliTheme.color.gray900,
                disabledContainerColor = Color.White.copy(alpha = alpha),
                disabledContentColor = WepliTheme.color.gray400,
            )
        }
    }
}

@Composable
fun WepliBasicButton(
    title: String,
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonStyle: WepliButtonStyle,
) {
    TextButton(
        onClick = { onClick() },
        colors = buttonStyle.colors(),
        shape = RoundedCornerShape(8.dp),
        enabled = isEnabled,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(
            text = title,
            style = WepliTheme.typo.body1,
        )
    }
}

@Preview
@Composable
fun WepliBasicButtonPreview() {
    Column(
        modifier = Modifier
            .background(WepliTheme.color.black)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        WepliBasicButton(
            title = "작성 완료",
            isEnabled = true,
            onClick = {},
            buttonStyle = WepliButtonStyle.Basic,
        )

        WepliBasicButton(
            title = "작성 완료",
            isEnabled = false,
            onClick = {},
            buttonStyle = WepliButtonStyle.Basic,
        )

        WepliBasicButton(
            title = "작성 완료",
            isEnabled = true,
            onClick = {},
            buttonStyle = WepliButtonStyle.Transparent(),
        )

        WepliBasicButton(
            title = "작성 완료",
            isEnabled = false,
            onClick = {},
            buttonStyle = WepliButtonStyle.Transparent(),
        )
    }
}