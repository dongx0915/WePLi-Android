package com.wepli.feature.photocard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wepli.designsystem.R
import com.wepli.shared.feature.mock.songMockData
import com.wepli.shared.feature.uimodel.photocard.PhotoCardUiData
import com.wepli.uimodel.music.SongUiData
import common.ShimmerSkeleton
import extensions.compose.shimmerEffect
import image.AsyncImageWithPreview
import theme.WepliTheme

@Preview
@Composable
fun PhotoCardComponent3Preview() {
    PhotoCardComponent3(
        photoCardInfo = PhotoCardUiData(
            nickname = "Chuu",
            profileImg = "https://scontent-gmp1-1.cdninstagram.com/v/t51.29350-15/405770843_694891179472237_6454586683999531682_n.webp?stp=dst-jpg_e35_p1080x1080_tt6&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xNDQweDE3OTkuc2RyLmYyOTM1MC5kZWZhdWx0X2ltYWdlIn0&_nc_ht=scontent-gmp1-1.cdninstagram.com&_nc_cat=101&_nc_oc=Q6cZ2AHAUprNmOpWYKGholr7o1017cub4YZVUiCmK_jW_avZwV7eBK161QTXm7wWdW9mtyM&_nc_ohc=lGBWYYgag1YQ7kNvgHebxbs&_nc_gid=c8be5b7b91d04decb1d328ed91fa4c8e&edm=AP4sbd4BAAAA&ccb=7-5&ig_cache_key=MzI0ODEyNDg1NTI5MDA2NDU1MA%3D%3D.3-ccb7-5&oh=00_AYDUTq2I7qV-IZQYVtDA8Mdos2cqzBEcHDR87sD1AsP2Fw&oe=67BF6F69&_nc_sid=7a9f4b",
            userTendency = "INTP",
            oneLineIntro = "I'm a singer",
            favoriteSong = songMockData.first(),
            instagramId = "chuu_loona"
        ),
        isEnabledShimmer = false,
    )
}

@Composable
fun PhotoCardComponent3(
    photoCardInfo: PhotoCardUiData,
    isEnabledShimmer: Boolean,
    modifier: Modifier = Modifier
) {
    val cardWidth = 247f
    val cardHeight = 354f

    Box(
        modifier
            .border(
                width = 1.dp,
                color = WepliTheme.color.gray100,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .background(color = WepliTheme.color.black)
            .widthIn(max = cardWidth.dp)
            .heightIn(max = cardHeight.dp)
            .aspectRatio(cardWidth / cardHeight)
    ) {
        // 배경으로 이미지가 들어가야하니 미리 캐싱이 필요함
        // 곡 선택 페이지에서 큰 사이즈의 이미지를 로딩해서 미리 캐싱하도록 함
        Box {
            AsyncImageWithPreview(
                modifier = Modifier
                    .fillMaxSize()
                    .blur(8.dp),
                imageUrl = photoCardInfo.favoriteSong.getImageUrl(),
                previewImage = painterResource(R.drawable.img_placeholder_eunbin),
                allowHardware = false,
                contentScale = ContentScale.Crop,
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = WepliTheme.color.black.copy(alpha = 0.5f))
            ) {}
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp, start = 20.dp, end = 12.dp, bottom = 20.dp)
        ) {
            Text(
                text = photoCardInfo.instagramId,
                style = WepliTheme.typo.caption1,
                color = WepliTheme.color.gray800,
            )
            Text(
                text = photoCardInfo.nickname,
                style = WepliTheme.typo.title3,
                color = WepliTheme.color.gray900,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = photoCardInfo.oneLineIntro,
                style = WepliTheme.typo.body5,
                color = WepliTheme.color.gray800,
            )

            ProfilePhoto(
                profileImageUrl = photoCardInfo.favoriteSong.getImageUrl(),
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.End)
                    .padding(vertical = 24.dp)
                    .padding(end = 20.dp)
            )

            FavoriteSongComponent(favoriteSong = photoCardInfo.favoriteSong, modifier = Modifier.weight(1f))
        }

        if (isEnabledShimmer) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .shimmerEffect(radius = 12.dp, duration = 2000, delay = 1500)
            )
        }
    }
}

@Composable
private fun FavoriteSongComponent(favoriteSong: SongUiData, modifier: Modifier = Modifier) {
    Row {
        Column(modifier = modifier.weight(1f)) {
            Text(
                text = favoriteSong.title,
                style = WepliTheme.typo.subTitle5,
                color = WepliTheme.color.gray900,
            )
            Text(
                text = favoriteSong.artistName,
                style = WepliTheme.typo.caption2,
                color = WepliTheme.color.gray800,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .height(2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = WepliTheme.color.gray900.copy(alpha = 0.2f),
                        )
                        .fillMaxSize(),
                    content = {}
                )
                Box(
                    modifier = Modifier
                        .background(
                            color = WepliTheme.color.gray900,
                        )
                        .fillMaxHeight()
                        .fillMaxWidth(0.3f),
                    content = {}
                )
            }
        }

        Spacer(modifier = Modifier.width(40.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_more_dot),
            contentDescription = null,
            tint = WepliTheme.color.gray900,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.Bottom)
        )
    }
}

@Composable
private fun ProfilePhoto(
    profileImageUrl: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        PolaroidPhoto(
            imageUrl = profileImageUrl,
            modifier = Modifier.graphicsLayer {
            rotationZ = -10f
        })
        PolaroidPhoto(
            imageUrl = profileImageUrl,
            modifier = Modifier
        )
    }
}

@Composable
private fun PolaroidPhoto(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    val polaroidWidth = 100.dp
    val polaroidHeight = 122.dp
    val photoWidth = 90.dp
    val photoHeight = 93.dp

    Box(
        modifier = modifier
            .background(color = WepliTheme.color.gray900)
            .aspectRatio(polaroidWidth / polaroidHeight)
            .padding(top = 6.dp, start = 6.dp, end = 6.dp)
    ) {
        AsyncImageWithPreview(
            imageUrl = imageUrl,
            previewImage = painterResource(id = R.drawable.img_placeholder_eunbin),
            allowHardware = false,
            loadingContent = {
                ShimmerSkeleton()
            },
            modifier = Modifier
                .aspectRatio(photoWidth / photoHeight)
        )
    }
}