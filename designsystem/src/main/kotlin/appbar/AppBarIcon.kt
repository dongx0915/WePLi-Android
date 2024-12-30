package appbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wepli.designsystem.R
import common.NotificationDot
import theme.WepliTheme

sealed interface AppBarIconType {
    val iconResource: Int
    val iconColor: @Composable () -> Color
    val onClick: () -> Unit

    data class Back(
        override val iconResource: Int = R.drawable.ic_arrow_back,
        override val iconColor: @Composable () -> Color = { WepliTheme.color.white },
        override val onClick: () -> Unit = {},
        val offset: Dp = (-16).dp,
    ) : AppBarIconType

    data class Search(
        override val iconResource: Int = R.drawable.ic_search,
        override val iconColor: @Composable () -> Color = { WepliTheme.color.white },
        override val onClick: () -> Unit = {},
        val offset: Dp = (-16).dp,
    ) : AppBarIconType

    data class Notification(
        override val iconResource: Int = R.drawable.ic_alarm,
        override val iconColor: @Composable () -> Color = { WepliTheme.color.white },
        override val onClick: () -> Unit = {},
        val offset: Dp = (-16).dp,
        val badgeVisibility: Boolean = true,
    ) : AppBarIconType

    data class Like(
        override val iconResource: Int = R.drawable.ic_heart,
        override val iconColor: @Composable () -> Color = { WepliTheme.color.white },
        override val onClick: () -> Unit = {},
        val likedIconResource: Int = R.drawable.ic_heart_filled,
        val isLiked: Boolean,
    ) : AppBarIconType

    data class More(
        override val iconResource: Int = R.drawable.ic_more_dot,
        override val iconColor: @Composable () -> Color = { WepliTheme.color.white },
        override val onClick: () -> Unit = {},
    ) : AppBarIconType
}

@Composable
fun AppBarIcon(icon: AppBarIconType) {
    when (icon) {
        is AppBarIconType.Back -> {
            AppBarIcon(
                iconResource = icon.iconResource,
                iconColor = icon.iconColor(),
                onClick = { icon.onClick() }
            )
        }

        is AppBarIconType.Search -> {
            AppBarIcon(
                iconResource = icon.iconResource,
                iconColor = icon.iconColor(),
                onClick = { icon.onClick() }
            )
        }

        is AppBarIconType.Notification -> {
            AppBarIcon(
                iconResource = icon.iconResource,
                iconColor = icon.iconColor(),
                badgeVisible = icon.badgeVisibility,
                onClick = { icon.onClick() }
            )
        }

        is AppBarIconType.More -> {
            AppBarIcon(
                iconResource = icon.iconResource,
                iconColor = icon.iconColor(),
                onClick = { icon.onClick() }
            )
        }

        is AppBarIconType.Like -> {
            AppBarIcon(
                iconResource = if (icon.isLiked) icon.likedIconResource else icon.iconResource,
                iconColor = if (icon.isLiked) Color.Unspecified else icon.iconColor(),
                onClick = { icon.onClick() }
            )
        }
    }
}

@Composable
private fun AppBarIcon(
    modifier: Modifier = Modifier,
    @DrawableRes iconResource: Int,
    iconColor: Color = WepliTheme.color.white,
    badgeVisible: Boolean = false,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = { onClick.invoke() },
        modifier = modifier.size(40.dp)
    ) {
        Box {
            Icon(
                painter = painterResource(id = iconResource),
                tint = iconColor,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.Center)
            )

            if (badgeVisible) {
                NotificationDot(
                    modifier = Modifier
                        .size(5.dp) // Notification Dot의 크기
                        .align(Alignment.TopEnd) // 오른쪽 상단에 위치
                        .offset(x = 3.dp, y = 0.dp) // 아이콘과 약간의 간격을 위한 오프셋 조정
                )
            }
        }
    }
}
