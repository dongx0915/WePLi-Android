package appbar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wepli.designsystem.R
import common.NotificationDot
import theme.WepliTheme

@Immutable
sealed interface AppBarIconType {
    val onClick: () -> Unit
}

sealed interface IconType : AppBarIconType {

    @get:DrawableRes val iconResource: Int
    val iconColor: @Composable () -> Color

    data class Back(
        override val iconResource: Int = R.drawable.ic_arrow_back,
        override val iconColor: @Composable () -> Color = { WepliTheme.color.white },
        override val onClick: () -> Unit = {},
        val offset: Dp = (-16).dp,
    ) : IconType

    data class Search(
        override val iconResource: Int = R.drawable.ic_search,
        override val iconColor: @Composable () -> Color = { WepliTheme.color.white },
        override val onClick: () -> Unit = {},
        val offset: Dp = (-16).dp,
    ) : IconType

    data class Notification(
        override val iconResource: Int = R.drawable.ic_alarm,
        override val iconColor: @Composable () -> Color = { WepliTheme.color.white },
        override val onClick: () -> Unit = {},
        val offset: Dp = (-16).dp,
        val badgeVisibility: Boolean = true,
    ) : IconType

    data class Like(
        override val iconResource: Int = R.drawable.ic_heart,
        override val iconColor: @Composable () -> Color = { WepliTheme.color.white },
        override val onClick: () -> Unit = {},
        val likedIconResource: Int = R.drawable.ic_heart_filled,
        val isLiked: Boolean,
    ) : IconType

    data class More(
        override val iconResource: Int = R.drawable.ic_more_dot,
        override val iconColor: @Composable () -> Color = { WepliTheme.color.white },
        override val onClick: () -> Unit = {},
    ) : IconType

    data class Save(
        override val iconResource: Int = R.drawable.ic_download,
        override val iconColor: @Composable () -> Color = { WepliTheme.color.white },
        override val onClick: () -> Unit = {},
    ) : IconType
}

sealed interface TextType : AppBarIconType {
    @get:StringRes val textResource: Int

    data class Solid(
        val color: Color,
        override val textResource: Int,
        override val onClick: () -> Unit = {},
    ) : TextType

    data class Gradient(
        val brush: Brush,
        override val textResource: Int,
        override val onClick: () -> Unit = {},
    ) : TextType
}

@Composable
fun AppBarIcon(icon: AppBarIconType) {
    when (icon) {
        is IconType -> {
            HandleIconType(icon)
        }

        is TextType -> {
            HandleTextType(icon)
        }
    }
}

@Composable
private fun HandleIconType(icon: IconType) {
    when (icon) {
        is IconType.Notification -> {
            AppBarIcon(
                iconResource = icon.iconResource,
                iconColor = icon.iconColor(),
                badgeVisible = icon.badgeVisibility,
                onClick = icon.onClick
            )
        }

        is IconType.Like -> {
            AppBarIcon(
                iconResource = if (icon.isLiked) icon.likedIconResource else icon.iconResource,
                iconColor = if (icon.isLiked) Color.Unspecified else icon.iconColor(),
                onClick = icon.onClick
            )
        }

        else -> {
            AppBarIcon(
                iconResource = icon.iconResource,
                iconColor = icon.iconColor(),
                onClick = icon.onClick
            )
        }
    }
}

@Composable
private fun HandleTextType(textIcon: TextType) {
    when (textIcon) {
        is TextType.Solid -> {
            AppBarText(
                textResource = textIcon.textResource,
                textStyle = WepliTheme.typo.subTitle4.copy(
                    color = textIcon.color
                ),
                onClick = textIcon.onClick
            )
        }

        is TextType.Gradient -> {
            AppBarText(
                textResource = textIcon.textResource,
                textStyle = WepliTheme.typo.subTitle4.copy(
                    brush = textIcon.brush
                ),
                onClick = textIcon.onClick
            )
        }
    }
}

@Composable
private fun AppBarText(
    @StringRes textResource: Int,
    textStyle: TextStyle,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick.invoke() }
            .padding(start = 20.dp, end = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(textResource),
            style = textStyle,
            textAlign = TextAlign.Center
        )
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

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun AppBarElementsPreview() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun createIconPreview(icons: List<AppBarIconType>) {
        Column {
            icons.forEach {
                WepliAppBar(
                    containerColor = Color.Transparent,
                    title = "타이틀 예시",
                    showBackButton = true,
                    actionIcons = listOf { AppBarIcon(it) }
                )
            }
        }
    }

    val iconList = listOf(
        IconType.Search(),
        IconType.Notification(),
        TextType.Gradient(
            brush = WepliTheme.color.linear3,
            textResource = R.string.design_system_example
        )
    )

    createIconPreview(icons = iconList)
}