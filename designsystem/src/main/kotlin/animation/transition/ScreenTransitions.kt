package animation.transition

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

object ScreenTransitions {

    fun defaultEnterTransition(): EnterTransition {
        return fadeIn(animationSpec = tween(durationMillis = 1000))
    }

    fun defaultExitTransition(): ExitTransition {
        return fadeOut(animationSpec = tween(durationMillis = 1000))
    }

    fun defaultPopEnterTransition(): EnterTransition? = null

    fun defaultPopExitTransition(): ExitTransition? = null
}