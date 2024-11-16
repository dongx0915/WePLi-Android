package appbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wepli.designsystem.R
import theme.WePLiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WePLiAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    containerColor: Color = Color.Black,
    contentsColor: Color = Color.White,
    showLogo: Boolean = false,
    showBackButton: Boolean = false,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onClickBack: (() -> Unit)? = null,
    actionIcons: List<@Composable () -> Unit> = emptyList(),
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = WePLiTheme.typo.subTitle3,
                color = contentsColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        navigationIcon = {
            if (showLogo) {
                Image(
                    painter = painterResource(id = R.drawable.ic_wepli_logo_white),
                    contentDescription = null,
                    modifier = Modifier
                        .size(44.dp)
                        .padding(horizontal = 10.dp)
                        .offset(x = 6.dp)
                )
            }

            if (showBackButton) {
                AppBarIcon(
                    icon = AppBarIconType.Back(
                        iconColor = { contentsColor },
                        onClick = { onClickBack?.invoke() }
                    )
                )
            }
        },
        actions = {
            actionIcons.forEach { actionIcon ->
                actionIcon()
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
            actionIcons = listOf {
                AppBarIcon(icon = AppBarIconType.Search())
                AppBarIcon(icon = AppBarIconType.Notification())
            }
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