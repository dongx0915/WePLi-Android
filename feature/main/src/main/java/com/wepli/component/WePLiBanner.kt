package com.wepli.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.wepli.feature.main.R
import theme.WePLiTheme
import util.OrientationPreviews


sealed interface WePLiBannerType {
    @Composable fun title(): String
    @Composable fun subTitle(): String
    @Composable fun bannerImg(): Painter
    @Composable fun backgroundColor(): Color
    @Composable fun titleTextStyle(): TextStyle = WePLiTheme.typo.subTitle5
    @Composable fun subTitleTextStyle(): TextStyle = WePLiTheme.typo.subTitle7

    data object Twitter : WePLiBannerType {
        @Composable
        override fun title(): String = "실시간 통합 순위를 한 눈에!"

        @Composable
        override fun subTitle(): String = "오직 위플리에서만"

        @Composable
        override fun bannerImg(): Painter = painterResource(id = R.drawable.img_banner_twitter)

        @Composable
        override fun backgroundColor(): Color = Color(0xFF6398FF)
    }

    data object Instagram : WePLiBannerType {
        @Composable
        override fun title(): String = "감성 가득한 일상 보러가기"

        @Composable
        override fun subTitle(): String = "위플리 인스타그램 OPEN!"

        @Composable
        override fun bannerImg(): Painter = painterResource(id = R.drawable.img_banner_instagram)

        @Composable
        override fun backgroundColor(): Color = Color(0xFFFA67D0)
    }
}

@OrientationPreviews
@Composable
fun WePLiBannerPreview() {
    Column {
        WePLiBanner(modifier = Modifier.fillMaxWidth(), bannerType = WePLiBannerType.Twitter)
        Spacer(modifier = Modifier.height(12.dp))
        WePLiBanner(modifier = Modifier.fillMaxWidth(), bannerType = WePLiBannerType.Instagram)
    }
}

@Composable
fun WePLiBanner(
    modifier: Modifier,
    bannerType: WePLiBannerType
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bannerType.backgroundColor())
            .height(84.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 20.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = bannerType.subTitle(),
                style = bannerType.subTitleTextStyle(),
                color = WePLiTheme.color.gray900,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = bannerType.title(),
                style = bannerType.titleTextStyle(),
                color = WePLiTheme.color.white,
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Image(
            modifier = Modifier
                .fillMaxHeight()
                .padding(end = 20.dp)
                .aspectRatio(100f / 76f)
                .sizeIn(maxWidth = 100.dp, maxHeight = 76.dp)
                .align(Alignment.CenterVertically),
            painter = bannerType.bannerImg(),
            alignment = Alignment.CenterEnd,
            contentDescription = null
        )
    }
}