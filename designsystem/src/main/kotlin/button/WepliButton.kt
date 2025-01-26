package button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import theme.WepliTheme

@Composable
fun WepliBasicButton(
    title: String,
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = ButtonDefaults.buttonColors(
        containerColor = WepliTheme.color.gray000,
        contentColor = WepliTheme.color.gray900,
        disabledContainerColor = WepliTheme.color.gray050,
        disabledContentColor = WepliTheme.color.gray400,
    )

    TextButton(
        onClick = { onClick() },
        colors = colors,
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
        modifier = Modifier.background(WepliTheme.color.black).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        WepliBasicButton(
            title = "작성 완료",
            isEnabled = true,
            onClick = {},
        )

        WepliBasicButton(
            title = "작성 완료",
            isEnabled = false,
            onClick = {},
        )
    }
}