package component.switch

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import theme.WepliTheme

// Brush 또는 Color를 담을 수 있는 래퍼
sealed class SwitchTrackBrush {
    data class Solid(val color: Color) : SwitchTrackBrush()
    data class Gradient(val brush: Brush) : SwitchTrackBrush()

    @SuppressLint("ModifierFactoryExtensionFunction")
    @Composable
    fun toModifier(): Modifier = when (this@SwitchTrackBrush) {
        is Solid -> Modifier.background(color)
        is Gradient -> Modifier.background(brush)
    }
}

class WepliTrackColors internal constructor(
    private val checkedTrackColor: SwitchTrackBrush,
    private val uncheckedTrackColor: SwitchTrackBrush,
    private val disabledTrackColor: SwitchTrackBrush,
) {

    @SuppressLint("ModifierFactoryExtensionFunction")
    @Composable
    fun trackColorModifier(enabled: Boolean, checked: Boolean): Modifier {
        val target = when {
            enabled.not() -> disabledTrackColor
            checked -> checkedTrackColor
            else -> uncheckedTrackColor
        }

        return target.toModifier()
    }
}

class WepliThumbStyle internal constructor(
    private val checkedThumbColor: Color,
    private val uncheckedThumbColor: Color,
    private val disabledThumbColor: Color,
    val elevation: Dp,
) {

    fun thumbColor(enabled: Boolean, checked: Boolean): Color {
        return when {
            enabled.not() -> disabledThumbColor
            checked -> checkedThumbColor
            else -> uncheckedThumbColor
        }
    }
}

object WepliSwitchDefaults {

    @Composable
    fun trackColors(
        checkedTrackColor: SwitchTrackBrush = SwitchTrackBrush.Gradient(WepliTheme.color.linear3),
        uncheckedTrackColor: SwitchTrackBrush = SwitchTrackBrush.Solid(WepliTheme.color.gray400),
        disabledTrackColor: SwitchTrackBrush = SwitchTrackBrush.Solid(WepliTheme.color.gray400),
    ): WepliTrackColors = WepliTrackColors(
        checkedTrackColor = checkedTrackColor,
        uncheckedTrackColor = uncheckedTrackColor,
        disabledTrackColor = disabledTrackColor,
    )

    @Composable
    fun thumbStyle(
        checkedThumbColor: Color = WepliTheme.color.white,
        uncheckedThumbColor: Color = WepliTheme.color.white,
        disabledThumbColor: Color = WepliTheme.color.gray100,
        elevation: Dp = 4.dp,
    ): WepliThumbStyle = WepliThumbStyle(
        checkedThumbColor = checkedThumbColor,
        uncheckedThumbColor = uncheckedThumbColor,
        disabledThumbColor = disabledThumbColor,
        elevation = elevation,
    )
}