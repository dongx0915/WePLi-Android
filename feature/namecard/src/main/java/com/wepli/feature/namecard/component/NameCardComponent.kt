package com.wepli.feature.namecard.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wepli.designsystem.R
import com.wepli.shared.feature.mock.songMockData
import com.wepli.shared.feature.uimodel.namecard.NameCardUiData
import common.ShimmerSkeleton
import extensions.compose.toPx
import image.AsyncImageWithPreview
import theme.WepliTheme

@Preview
@Composable
fun NameCardComponentPreview() {
    NameCardComponent(
        nameCardInfo = NameCardUiData(
            nickname = "Chuu",
            userTendency = "INTP",
            oneLineIntro = "I'm a singer",
            favoriteSong = songMockData.first(),
            instagramId = "chuu_loona"
        )
    )
}

@Composable
fun NameCardComponent(
    nameCardInfo: NameCardUiData,
    modifier: Modifier = Modifier
) {
    val imageSize = 146.dp
    val nameCardWidth = 247f
    val nameCardHeight = 354f

    Column(
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
            .padding(top = 36.dp, bottom = 20.dp, start = 20.dp),
    ) {
        Text(
            text = nameCardInfo.nickname,
            style = WepliTheme.typo.title2,
            color = WepliTheme.color.white,
        )
        Text(
            text = nameCardInfo.userTendency,
            style = WepliTheme.typo.caption2.copy(
                fontWeight = FontWeight.Light,
            ),
            color = WepliTheme.color.gray800,
        )

        Spacer(Modifier.height(24.dp))
        Text(
            modifier = Modifier.padding(end = 20.dp),
            text = nameCardInfo.oneLineIntro,
            style = WepliTheme.typo.body5,
            color = WepliTheme.color.gray600,
        )

        Spacer(Modifier.weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy((-30).dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
            ){
                Text(
                    text = "BEST PICK",
                    style = WepliTheme.typo.caption2.copy(
                        fontSize = 8.sp,
                        brush = WepliTheme.color.linear3,
                    ),
                    color = WepliTheme.color.gray800
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = nameCardInfo.favoriteSong.title,
                    style = WepliTheme.typo.body6.copy(
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 14.sp
                    ),
                    color = WepliTheme.color.gray900,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                )
                Text(
                    text = nameCardInfo.favoriteSong.artistName,
                    style = WepliTheme.typo.caption1,
                    color = WepliTheme.color.gray800
                )
            }

            FavoriteSongComponent(
                imageSize = imageSize,
                imageUrl = nameCardInfo.favoriteSong.getImageUrl(imageSize.toPx()),
                modifier = Modifier.offset(x = 30.dp)
            )
        }

        Spacer(Modifier.weight(1f))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_instagram_color),
                contentDescription = null,
            )

            Text(
                text = nameCardInfo.instagramId,
                style = WepliTheme.typo.body6,
                color = WepliTheme.color.gray800
            )
        }
    }
}

@Composable
private fun FavoriteSongComponent(imageSize: Dp, imageUrl: String, modifier: Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(150.dp),
            painter = painterResource(R.drawable.img_cd_background),
            contentDescription = null,
        )

        AsyncImageWithPreview(
            modifier = Modifier
                .size(imageSize)
                .clip(CircleShape),
            imageUrl = imageUrl,
            previewImage = painterResource(R.drawable.img_placeholder_chuu),
            loadingContent = {
                ShimmerSkeleton(
                    modifier = Modifier
                        .size(imageSize)
                        .clip(CircleShape),
                )
            }
        )
    }
}