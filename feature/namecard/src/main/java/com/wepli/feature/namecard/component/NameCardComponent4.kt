package com.wepli.feature.namecard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wepli.designsystem.R
import com.wepli.shared.feature.mock.songMockData
import com.wepli.shared.feature.uimodel.namecard.NameCardUiData
import com.wepli.uimodel.music.SongUiData
import extensions.toEnglishMonthName
import image.AsyncImageWithPreview
import theme.WepliTheme
import java.util.Calendar
import java.util.Date

@Preview
@Composable
fun NameCardComponent4Preview() {
    NameCardComponent4(
        nameCardInfo = NameCardUiData(
            nickname = "Chuu",
            profileImg = "https://scontent-gmp1-1.cdninstagram.com/v/t51.29350-15/405770843_694891179472237_6454586683999531682_n.webp?stp=dst-jpg_e35_p1080x1080_tt6&efg=eyJ2ZW5jb2RlX3RhZyI6ImltYWdlX3VybGdlbi4xNDQweDE3OTkuc2RyLmYyOTM1MC5kZWZhdWx0X2ltYWdlIn0&_nc_ht=scontent-gmp1-1.cdninstagram.com&_nc_cat=101&_nc_oc=Q6cZ2AHAUprNmOpWYKGholr7o1017cub4YZVUiCmK_jW_avZwV7eBK161QTXm7wWdW9mtyM&_nc_ohc=lGBWYYgag1YQ7kNvgHebxbs&_nc_gid=c8be5b7b91d04decb1d328ed91fa4c8e&edm=AP4sbd4BAAAA&ccb=7-5&ig_cache_key=MzI0ODEyNDg1NTI5MDA2NDU1MA%3D%3D.3-ccb7-5&oh=00_AYDUTq2I7qV-IZQYVtDA8Mdos2cqzBEcHDR87sD1AsP2Fw&oe=67BF6F69&_nc_sid=7a9f4b",
            userTendency = "INTP",
            oneLineIntro = "I'm a singer",
            favoriteSong = songMockData.first(),
            instagramId = "chuu_loona"
        ),
    )
}

@Composable
fun NameCardComponent4(
    nameCardInfo: NameCardUiData,
    modifier: Modifier = Modifier
) {
    val today = remember { Date() }
    val nameCardWidth = 247f
    val nameCardHeight = 354f

    Box(
        modifier
            .border(
                width = 1.dp,
                color = WepliTheme.color.gray100,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .background(color = WepliTheme.color.black)
            .widthIn(max = nameCardWidth.dp)
            .heightIn(max = nameCardHeight.dp)
            .aspectRatio(nameCardWidth / nameCardHeight)
    ) {
        // 배경으로 이미지가 들어가야하니 미리 캐싱이 필요함
        // 곡 선택 페이지에서 큰 사이즈의 이미지를 로딩해서 미리 캐싱하도록 함
        Box {
            AsyncImageWithPreview(
                modifier = Modifier
                    .fillMaxSize()
                    .blur(8.dp),
                imageUrl = nameCardInfo.favoriteSong.getImageUrl(),
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
            Row {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString(),
                        style = WepliTheme.typo.default.copy(
                            fontWeight = FontWeight.ExtraLight,
                            fontSize = 48.sp,
                        ),
                        color = WepliTheme.color.gray800,
                    )
                    Text(
                        text = today.toEnglishMonthName(),
                        style = WepliTheme.typo.default.copy(
                            fontWeight = FontWeight.Light,
                            fontSize = 20.sp,
                        ),
                        color = WepliTheme.color.gray900,
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                UserProfile(
                    nickname = nameCardInfo.nickname,
                    profileImg = nameCardInfo.profileImg,
                    tendency = nameCardInfo.userTendency
                )
            }


            Spacer(modifier = Modifier.weight(1f))
            FavoriteSongComponent(favoriteSong = nameCardInfo.favoriteSong, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun UserProfile(
    nickname: String,
    profileImg: String,
    tendency: String,
) {
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .background(
                WepliTheme.color.white.copy(alpha = 0.2f)
            )
            .padding(vertical = 6.dp)
            .padding(start = 6.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        AsyncImageWithPreview(
            imageUrl = profileImg,
            previewImage = painterResource(R.drawable.img_placeholder_eunbin),
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
        )
        
        Column { 
            Text(
                text = nickname,
                style = WepliTheme.typo.body6.copy(
                    fontWeight = FontWeight.Medium,
                ),
                color = WepliTheme.color.gray900,
            )

            Text(
                text = tendency,
                style = WepliTheme.typo.overline.copy(
                    fontWeight = FontWeight.Light,
                ),
                color = WepliTheme.color.gray700,
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