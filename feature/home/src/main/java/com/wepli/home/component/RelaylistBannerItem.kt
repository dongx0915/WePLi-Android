package com.wepli.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.wepli.designsystem.R
import com.wepli.shared.feature.mock.relaylistMockData
import custom.BlurBackgroundOverlay
import extensions.compose.pagerFadeTransition
import image.AsyncImageWithPreview
import model.relaylist.Relaylist
import theme.WepliTheme
import kotlin.math.absoluteValue

@Preview
@Composable
fun RelaylistBannerPreview() {
    RelaylistBannerComponent(
        item = relaylistMockData.first(),
        scaleSizeRatio = 0.8f,
        pageOffset = 0f
    )
}

@Composable
fun RelaylistBannerComponent(
    item: Relaylist,
    scaleSizeRatio: Float,
    pageOffset: Float,
) {
    Box(
        modifier = Modifier
            .aspectRatio(5f / 6f)
            .graphicsLayer {
                val fraction = pageOffset.absoluteValue.coerceIn(0f, 1f)
                val scale = lerp(1f, scaleSizeRatio, fraction).also {
                    scaleX = it
                    scaleY = it
                }

                translationX = size.width * (1 - scale) / 2 * (if (pageOffset > 0) 1 else -1)
            }
            .clip(RoundedCornerShape(12.dp)),
    ) {
        Box(
            modifier = Modifier.background(Color.Transparent),
            contentAlignment = Alignment.BottomStart,
        ) {
            AsyncImageWithPreview(
                modifier = Modifier.fillMaxSize(),
                imageUrl = item.coverImgUrl,
                previewImage = painterResource(id = R.drawable.img_placeholder_eunbin),
                contentScale = ContentScale.Crop,
            )

            // BlurBackgroundOverlay 추가
            item.artwork?.let {
                BlurBackgroundOverlay(
                    modifier = Modifier.fillMaxSize(), // Box 전체를 차지하도록 설정
                    blurModifier = Modifier.fillMaxSize(),
                    backgroundColor = Color(it.backgroundColor),
                    colorStopRange = 0f..0.5f, // 예제의 알파 값 범위
                    imageResId = null // 필요에 따라 이미지 리소스를 전달
                )
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 20.dp)
                    .graphicsLayer {
                        alpha = 1 - (pageOffset.absoluteValue * 2).coerceIn(0f, 1f)
                        translationY = lerp(
                            start = 0f,
                            stop = 100f,
                            fraction = pageOffset.absoluteValue.coerceIn(0f, 1f),
                        )
                    },
            ) {
                Text(
                    text = item.title,
                    color = WepliTheme.color.gray900,
                    style = WepliTheme.typo.title2.copy(
                        fontWeight = FontWeight.Bold
                    ),
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = item.description,
                    color = WepliTheme.color.gray800,
                    style = WepliTheme.typo.body3,
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = blurOverlayAlpha))
            )
        }
    }
}


@Composable
fun RelaylistBackground(
    modifier: Modifier = Modifier,
    item: Relaylist,
    page: Int,
    bottomPagerState: PagerState,
) {
    Box(
        modifier = modifier
            .pagerFadeTransition(page, bottomPagerState) // 전환 효과 적용
            .blur(70.dp)
    ) {
        AsyncImageWithPreview(
            modifier = Modifier.fillMaxSize(),
            imageUrl = item.coverImgUrl,
            previewImage = painterResource(id = R.drawable.img_placeholder_eunbin),
            contentScale = ContentScale.FillBounds,
        )

        BlurBackgroundOverlay(
            modifier = Modifier.matchParentSize(),
            blurModifier = Modifier.matchParentSize(),
            colorStopRange = 0f..1f
        )
    }
}