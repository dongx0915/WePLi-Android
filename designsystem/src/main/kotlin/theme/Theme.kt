package theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Density
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun WePLiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = remember(darkTheme) {
        if (darkTheme) darkColors() else lightColors()
    }

    // 시스템 하단바 색상 설정
    val view = LocalView.current
    val window = (view.context as? Activity)?.window
    if (window != null) {
        LaunchedEffect(darkTheme) {
            // 하단 네비게이션 바의 배경색을 검은색으로 설정
            window.navigationBarColor = Color.Black.toArgb()

            // 다크 모드에 따른 아이콘 색상 설정
            val controller = WindowInsetsControllerCompat(window, view)
            controller.isAppearanceLightStatusBars = false // 아이콘을 항상 밝게 설정
            controller.isAppearanceLightNavigationBars = false
        }
    }

    CompositionLocalProvider(
        LocalWePLiColors provides colorScheme,
        LocalWePLiTypography provides WepliTheme.typo,
        LocalDensity provides Density(
            density = LocalDensity.current.density,
            fontScale = 1f
        )
    ) {
        MaterialTheme(
            content = content
        )
    }
}

@Stable
object WepliTheme {

    val color: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalWePLiColors.current

    val typo: WePLiTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalWePLiTypography.current
}