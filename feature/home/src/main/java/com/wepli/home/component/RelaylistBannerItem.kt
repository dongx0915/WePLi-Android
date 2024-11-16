package com.wepli.home.component

import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.wepli.designsystem.R
import custom.BlurBackgroundOverlay
import image.AsyncImageWithPreview
import model.relaylist.Relaylist
import theme.WePLiTheme
import kotlin.math.absoluteValue

@Preview
@Composable
fun RelaylistBannerPreview() {
    RelaylistBannerComponent(
        item = Relaylist(
            title = "손님들이 물어보는\n카페 BGM",
            description = "트는 순간 바로 인생샷 건졌던\n카페로 순간이동하는 플레이리스트",
            coverImgUrl = "https://cdn.music-flo.com/poc/p/image/channel/rep/20240509/7f46bce952e548919ef29cf897f6410c.jpg/dims/resize/1000x1000/quality/100",
            artwork = Relaylist.Artwork(0xFF439EE6)
        ),
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
                lerp(
                    start = 1f,
                    stop = scaleSizeRatio,
                    fraction = pageOffset.absoluteValue.coerceIn(0f, 1f),
                ).let {
                    scaleX = it
                    scaleY = it
                    val sign = if (pageOffset > 0) 1 else -1
                    translationX = sign * size.width * (1 - it) / 2
                }
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
                    colorStopRange = 0f..1f, // 예제의 알파 값 범위
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
                    color = WePLiTheme.color.gray900,
                    style = WePLiTheme.typo.title2,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = item.description,
                    color = WePLiTheme.color.gray900,
                    style = WePLiTheme.typo.body5,
                )
            }
        }
    }
}