package compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun HighlightedText(
    text: String,
    delimiter: String,
    textStyle: TextStyle,
    highlightColor: Brush
) {
    val annotatedString = buildAnnotatedString {
        val parts = text.split(delimiter)
        parts.forEachIndexed { index, part ->
            // delimiter로 감싸진 텍스트 (짝수 인덱스는 일반 텍스트)
            val spanStyle = if (index % 2 == 1) {
                SpanStyle(
                    brush = highlightColor,
                    fontSize = textStyle.fontSize
                )
            } else {
                SpanStyle(
                    color = textStyle.color,
                    fontSize = textStyle.fontSize
                )
            }

            withStyle(style = spanStyle) {
                append(part)
            }
        }
    }

    Text(
        text = annotatedString,
        style = textStyle
    )
}