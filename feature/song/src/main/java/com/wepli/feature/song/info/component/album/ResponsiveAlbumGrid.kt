package com.wepli.feature.song.info.component.album

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.wepli.shared.feature.uimodel.album.AlbumUiData
import extensions.compose.toPx

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalLayoutApi::class)
@Composable
fun ResponsiveAlbumGrid(albums: List<AlbumUiData>, modifier: Modifier = Modifier) {
    val activity = LocalActivity.current
    val windowSizeClass = activity?.let { calculateWindowSizeClass(it) }

    val spacing = 20.dp
    val itemsPerRow = when (windowSizeClass?.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 2
        WindowWidthSizeClass.Medium,
        WindowWidthSizeClass.Expanded -> 4
        else -> 2
    }

    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val itemWidthPx = (maxWidth.toPx() - spacing.toPx() * (itemsPerRow - 1)) / itemsPerRow
        val itemWidthDp = with(LocalDensity.current) { itemWidthPx.toDp() }

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            maxItemsInEachRow = itemsPerRow,
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            albums.forEach { album ->
                AlbumComponent(
                    album = album,
                    modifier = Modifier.width(itemWidthDp)
                )
            }
        }
    }
}