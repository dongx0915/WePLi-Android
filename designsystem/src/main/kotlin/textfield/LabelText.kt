package textfield

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import theme.WepliTheme

@Composable
fun FieldLabel(
    text: String,
    label: String,
    isRequired: Boolean,
    modifier: Modifier = Modifier,
) {
    val labelStyle = with(WepliTheme.typo) {
        if (isRequired) body4 else caption2
    }

    Row(
        modifier = modifier,
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