package appbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wepli.designsystem.R
import common.NotificationDot
import theme.WePLiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WePLiAppBar(
    title: String = "",
    containerColor: Color = Color.Black,
    contentsColor: Color = Color.White,
    showLogo: Boolean = false,
    showBackButton: Boolean = false,
    showSearchButton: Boolean = false,
    showNotificationButton: Boolean = false,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onClickBack: (() -> Unit)? = null,
    onClickSearch: (() -> Unit)? = null,
    onClickNotification: (() -> Unit)? = null,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = WePLiTheme.typo.subTitle3,
                color = Color.White
            )
        },
        navigationIcon = {
            if (showLogo) {
                Image(
                    painter = painterResource(id = R.drawable.ic_wepli_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(44.dp)
                        .padding(horizontal = 10.dp)
                        .offset(x = 6.dp)
                )
            }

            if (showBackButton) {
                AppBarIcon(
                    iconResource = R.drawable.ic_arrow_back,
                    onClick = { onClickBack?.invoke() }
                )
            }
        },
        actions = {
            if (showSearchButton) {
                AppBarIcon(
                    modifier = Modifier.offset(x = -6.dp),
                    iconResource = R.drawable.ic_search,
                    onClick = { onClickSearch?.invoke() }
                )
            }

            if (showNotificationButton) {
                AppBarIcon(
                    modifier = Modifier.offset(x = -6.dp),
                    iconResource = R.drawable.ic_alarm,
                    onClick = { onClickNotification?.invoke() },
                    badgeVisible = true
                )
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = containerColor,
            navigationIconContentColor = contentsColor,
            actionIconContentColor = contentsColor,
            titleContentColor = contentsColor
        )
    )
}

@Composable
fun AppBarIcon(
    modifier: Modifier = Modifier,
    @DrawableRes iconResource: Int,
    iconColor: Color = WePLiTheme.color.white,
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


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = false)
@Composable
fun AppBarPreview() {
    Column(
        modifier = Modifier.background(Color.Transparent)
    ) {
        WePLiAppBar(
            showLogo = true,
            showBackButton = false,
            showSearchButton = true,
            showNotificationButton = true,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = false)
@Composable
fun MainAppBarPreview() {
    Column(
        modifier = Modifier.background(Color.Transparent)
    ) {
        WePLiAppBar(
            title = "타이틀",
            showBackButton = true,
        )
    }
}