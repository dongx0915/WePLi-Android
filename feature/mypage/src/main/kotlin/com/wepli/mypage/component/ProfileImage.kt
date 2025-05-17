package com.wepli.mypage.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wepli.core.resources.R as CoreR
import image.AsyncImageWithPreview
import theme.WepliTheme

@Composable
fun ProfileImage(
    profileImgUrl: String,
    imageSize: Dp,
    modifier: Modifier = Modifier,
) {
    val imageModifier = Modifier
        .size(imageSize)
        .border(
            width = 1.dp,
            brush = WepliTheme.color.linear3,
            shape = CircleShape
        )
        .clip(CircleShape)

    Box(modifier = modifier) {
        AsyncImageWithPreview(
            modifier = imageModifier,
            imageUrl = profileImgUrl,
            previewImage = painterResource(CoreR.drawable.img_placeholder_eunbin)
        )

        Image(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(20.dp),
            painter = painterResource(id = CoreR.drawable.ic_profile_camera),
            contentDescription = null
        )
    }
}