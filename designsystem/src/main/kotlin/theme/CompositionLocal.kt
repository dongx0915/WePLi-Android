package theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import dev.chrisbanes.haze.HazeState

val LocalHazeState = compositionLocalOf { HazeState() }
internal val LocalWePLiColors = staticCompositionLocalOf { lightColors() }
internal val LocalWePLiTypography = staticCompositionLocalOf { WePLiTypography() }