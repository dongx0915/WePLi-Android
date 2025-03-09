package appbar

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wepli.shared.feature.mock.relaylistUiMockData
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import theme.LocalHazeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    content: @Composable (scrollState: LazyListState, paddingValues: PaddingValues) -> Unit
) {
    val scrollState = rememberLazyListState()
    val blurState = LocalHazeState.current

    ScrollableAppBar(
        scrollState = scrollState,
        backgroundColors = Color.Transparent to Color.Black.copy(0.5f),
        contentsColors = Color.White to Color.White,
        topBarComponent = { backgroundColor, contentsColor, _, scrollFraction ->
            WepliAppBar(
                modifier = Modifier
                    .hazeEffect(
                        state = blurState,
                        style = HazeStyle(
                            backgroundColor = backgroundColor,
                            blurRadius = (scrollFraction * 24).dp,
                            tint = HazeTint(color = backgroundColor),
                        ),
                    ),
                showLogo = true,
                showBackButton = false,
                containerColor = backgroundColor,
                contentsColor = contentsColor,
                actionIcons = listOf {
                    AppBarIcon(icon = AppBarIconType.Search())
                    AppBarIcon(icon = AppBarIconType.Notification())
                }
            )
        }
    ) { paddingValues ->
        content(scrollState, paddingValues)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistAppBar(
    playlistTitle: String,
    playlistIsLiked: Boolean,
    onClickLike: () -> Unit,
    navOnBack: () -> Unit,
    content: @Composable (scrollState: ScrollState, paddingValues: PaddingValues) -> Unit
) {

    val scrollState = rememberScrollState()
    ScrollableAppBar(
        scrollState = scrollState,
        backgroundColors = Color.Transparent to Color.Black,
        contentsColors = Color.White to Color.White,
        topBarComponent = { backgroundColor, contentsColor, isFullScrolled, _ ->
            WepliAppBar(
                title = if (isFullScrolled) playlistTitle else "",
                containerColor = backgroundColor,
                contentsColor = contentsColor,
                showBackButton = true,
                actionIcons = listOf {
                    AppBarIcon(
                        icon = AppBarIconType.Like(
                            isLiked = playlistIsLiked,
                            iconColor = { contentsColor },
                            onClick = { onClickLike() }
                        )
                    )
                    AppBarIcon(icon = AppBarIconType.More(iconColor = { contentsColor }))
                },
                onClickBack = { navOnBack() }
            )
        }
    ) { paddingValues ->
        content(scrollState, paddingValues)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RelaylistAppBar(
    onClickBack: () -> Unit,
    content: @Composable (scrollState: ScrollState, paddingValues: PaddingValues) -> Unit
) {
    val scrollState = rememberScrollState()

    ScrollableAppBar(
        scrollState = scrollState,
        backgroundColors = Color.Transparent to Color.Black.copy(alpha = 0.5f),
        contentsColors = Color.White to Color.White,
        topBarComponent = { backgroundColor, contentsColor, _, _ ->
            WepliAppBar(
                title = "",
                containerColor = backgroundColor,
                contentsColor = contentsColor,
                showBackButton = true,
                onClickBack = { onClickBack() },
            )
        }
    ) { paddingValues ->
        content(scrollState, paddingValues)
    }
}