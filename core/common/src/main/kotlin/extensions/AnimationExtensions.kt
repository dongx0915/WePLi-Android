package extensions

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

fun AnimatedContentTransitionScope<NavBackStackEntry>.enterAnimation(durationMillis: Int = 500) = slideIntoContainer(
    AnimatedContentTransitionScope.SlideDirection.Left,
    animationSpec = tween(durationMillis)
)