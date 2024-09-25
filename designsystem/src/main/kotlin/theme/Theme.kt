package theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember

@Composable
fun WePLiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = remember(darkTheme) {
        if (darkTheme) darkColors() else lightColors()
    }

    CompositionLocalProvider(
        LocalWePLiColors provides colorScheme,
        LocalWePLiTypography provides WePLiTheme.typography,
    ) {
        MaterialTheme(
            content = content
        )
    }
}

@Stable
object WePLiTheme {

    val color: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalWePLiColors.current

    val typography: WePLiTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalWePLiTypography.current
}