package common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import compose.shimmerEffect
import theme.WepliTheme

@Composable
fun ShimmerSkeleton(
    modifier: Modifier = Modifier,
    shimmerEffect: Dp = 4.dp
) {
    Box(
        modifier = modifier
            .background(color = WepliTheme.color.gray500)
            .shimmerEffect(shimmerEffect)
    )
}