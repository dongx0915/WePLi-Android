package com.wepli.playlist.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wepli.designsystem.R
import com.wepli.playlist.state.PlaylistState
import org.joda.time.LocalDate

@Composable
fun PlaylistHeader(
    state: PlaylistState,
    modifier: Modifier = Modifier
) {
    val playlist = state.playlist

    Box {
        // 헤더 백그라운드
        HeaderBackground(modifier = Modifier.matchParentSize())

        // 헤더 콘텐츠
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 20.dp, end = 20.dp)
        ) {
            PlaylistContentHeader(
                title = playlist.title,
                author = playlist.author
            )
            Spacer(modifier = Modifier.height(20.dp))
            PlaylistContentBody(
                description = playlist.description,
                bSideTrackCount = playlist.bSideTrack.size,
                createdAt = LocalDate(playlist.createdAt.time)
            )
            Spacer(modifier = Modifier.height(24.dp))
            PlaylistContentFooter()
        }
    }
}

@Composable
fun HeaderBackground(modifier: Modifier = Modifier) {
    val gradientBrush = remember {
        val color = Color(0xFF000000)

        Brush.verticalGradient(
            colors = listOf(
                color.copy(alpha = 0.67f),
                color.copy(alpha = 0.70f),
                color.copy(alpha = 0.82f),
                color.copy(alpha = 0.85f),
                color.copy(alpha = 1.0f),
            )
        )
    }

    Box(modifier = modifier.fillMaxWidth()) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .blur(50.dp),
            painter = painterResource(id = R.drawable.img_placeholder_eunbin),
            contentDescription = ""
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(brush = gradientBrush)
        )
    }
}