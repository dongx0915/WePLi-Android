package theme

import android.app.Activity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import dev.chrisbanes.haze.HazeState

val LocalHazeState = compositionLocalOf { HazeState() }

val LocalWindowWidthSizeClass = staticCompositionLocalOf { WindowWidthSizeClass.Compact }

internal val LocalWePLiColors = staticCompositionLocalOf { lightColors() }
internal val LocalWePLiTypography = staticCompositionLocalOf { WePLiTypography() }


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun ProvideWindowWidthSizeClass(
    activity: Activity?,
    content: @Composable () -> Unit
) {
    val configuration = LocalConfiguration.current
    val widthClass: WindowWidthSizeClass =
        if (activity != null) {
            calculateWindowSizeClass(activity).widthSizeClass
        } else {
            // Preview 등 대체 로직
            val wDp = configuration.screenWidthDp
            when {
                wDp < 600 -> WindowWidthSizeClass.Compact
                wDp < 840 -> WindowWidthSizeClass.Medium
                else -> WindowWidthSizeClass.Expanded
            }
        }

    CompositionLocalProvider(LocalWindowWidthSizeClass provides widthClass) {
        content()
    }
}