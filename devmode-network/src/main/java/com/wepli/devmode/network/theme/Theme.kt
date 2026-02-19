package com.wepli.devmode.network.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Density
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun NetworkLogTheme(
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
            // 다크 모드에 따른 아이콘 색상 설정
            val controller = WindowInsetsControllerCompat(window, view)
            controller.isAppearanceLightStatusBars = false // 아이콘을 항상 밝게 설정
            controller.isAppearanceLightNavigationBars = false
        }
    }

    CompositionLocalProvider(
        LocalNetworkLogColors provides colorScheme,
        LocalNetworkLogTypography provides NetworkLogTheme.typo,
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
object NetworkLogTheme {

    val color: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalNetworkLogColors.current

    val typo: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalNetworkLogTypography.current
}
