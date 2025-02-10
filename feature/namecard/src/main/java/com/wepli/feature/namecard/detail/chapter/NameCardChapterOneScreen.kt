package com.wepli.feature.namecard.detail.chapter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import button.WepliBasicButton
import button.WepliButtonStyle
import com.wepli.designsystem.R
import com.wepli.feature.namecard.detail.mvi.NameCardDetailUiState
import common.ShimmerSkeleton
import extensions.compose.toPx
import image.AsyncImageWithPreview
import theme.WepliTheme

@Composable
fun NameCardChapterOneScreen(
    state: NameCardDetailUiState,
    modifier: Modifier = Modifier,
    navOnNextPage: () -> Unit,
    navOnSongSearchScreen: () -> Unit,
) {
    Column(
        modifier = modifier.padding(horizontal = 24.dp)
    ) {
        Text(
            text = "가장 좋아하는 노래 1곡을 선택해 주세요",
            style = WepliTheme.typo.title1,
            color = WepliTheme.color.gray900
        )
        Spacer(modifier = Modifier.padding(top = 12.dp))
        Text(
            text = "선택한 노래는 명함에 추가되어 다른 사람들에게 보여질거예요",
            style = WepliTheme.typo.body4,
            color = WepliTheme.color.gray500
        )

        Spacer(modifier = Modifier.weight(1f))

        SelectedSongComponent(
            state = state,
            onClick = { navOnSongSearchScreen() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.weight(2f))
        WepliBasicButton(
            title = "선택완료",
            isEnabled = state.selectedFavoriteSong != null,
            onClick = { navOnNextPage() },
            modifier = Modifier
                .padding(bottom = 20.dp)
                .align(Alignment.CenterHorizontally),
            buttonStyle = WepliButtonStyle.Basic,
        )
    }
}

@Composable
fun SelectedSongComponent(
    state: NameCardDetailUiState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val selectedSong = state.selectedFavoriteSong
    val imageSize = 150.dp
    val selectedSongModifier = Modifier
        .clickable { onClick() }
        .clip(RoundedCornerShape(4.dp))
        .background(WepliTheme.color.gray150)
        .size(imageSize)

    Column(
        modifier = modifier
            .widthIn(max = 174.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(WepliTheme.color.gray000)
            .padding(12.dp)
    ) {
        if (selectedSong == null) {
            Box(
                modifier = selectedSongModifier,
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_plus_gradient),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
            }
        } else {
            AsyncImageWithPreview(
                imageUrl = selectedSong.getImageUrl(imageSize.toPx()),
                previewImage = painterResource(id = R.drawable.img_placeholder_eunbin),
                modifier = selectedSongModifier,
                loadingContent = {
                    ShimmerSkeleton(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .size(imageSize),
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = selectedSong?.title ?: "노래를 선택해주세요",
            style = WepliTheme.typo.subTitle5,
            color = WepliTheme.color.gray900
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = selectedSong?.artistName ?: "선택하면 화면에 표시됩니다",
            style = WepliTheme.typo.body6,
            color = WepliTheme.color.gray500
        )
    }
}