package com.wepli.devmode.fcm.presentation.component.common

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
import com.wepli.devmode.fcm.presentation.theme.DevModeTheme
import theme.WepliTheme

sealed interface DevModeButtonStyle {
    @Composable
    fun colors(): ButtonColors

    data object Basic : DevModeButtonStyle {
        @Composable
        override fun colors(): ButtonColors {
            return ButtonDefaults.buttonColors(
                containerColor = DevModeTheme.color.gray000,
                contentColor = DevModeTheme.color.gray900,
                disabledContainerColor = DevModeTheme.color.gray050,
                disabledContentColor = DevModeTheme.color.gray400,
            )
        }
    }

    data class Transparent(val alpha: Float = 0.0f) : DevModeButtonStyle {
        @Composable
        override fun colors(): ButtonColors {
            return ButtonDefaults.buttonColors(
                containerColor = Color.White.copy(alpha = alpha),
                contentColor = DevModeTheme.color.gray900,
                disabledContainerColor = Color.White.copy(alpha = alpha),
                disabledContentColor = DevModeTheme.color.gray400,
            )
        }
    }
}

@Composable
fun DevModeBasicButton(
    title: String,
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonStyle: DevModeButtonStyle,
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
fun DevModeBasicButtonPreview() {
    Column(
        modifier = Modifier
            .background(WepliTheme.color.black)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        DevModeBasicButton(
            title = "작성 완료",
            isEnabled = true,
            onClick = {},
            buttonStyle = DevModeButtonStyle.Basic,
        )

        DevModeBasicButton(
            title = "작성 완료",
            isEnabled = false,
            onClick = {},
            buttonStyle = DevModeButtonStyle.Basic,
        )

        DevModeBasicButton(
            title = "작성 완료",
            isEnabled = true,
            onClick = {},
            buttonStyle = DevModeButtonStyle.Transparent(),
        )

        DevModeBasicButton(
            title = "작성 완료",
            isEnabled = false,
            onClick = {},
            buttonStyle = DevModeButtonStyle.Transparent(),
        )
    }
}
