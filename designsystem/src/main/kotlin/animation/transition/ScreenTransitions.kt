package animation.transition

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

object ScreenTransitions {

    fun defaultEnterTransition(durationMillis: Int = 1000): EnterTransition {
        return fadeIn(animationSpec = tween(durationMillis = durationMillis))
    }

    fun defaultExitTransition(durationMillis: Int = 1000): ExitTransition {
        return fadeOut(animationSpec = tween(durationMillis = durationMillis))
    }

    fun defaultPopEnterTransition(): EnterTransition? = null

    fun defaultPopExitTransition(): ExitTransition? = null
}