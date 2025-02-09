package com.wepli.feature.namecard.detail.chapter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import button.WepliBasicButton
import com.wepli.designsystem.R
import theme.WepliTheme

@Composable
fun NameCardChapterOneScreen(modifier: Modifier = Modifier) {
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
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.weight(2f))
        WepliBasicButton(
            title = "선택완료",
            isEnabled = true,
            onClick = { },
            modifier = Modifier
                .padding(bottom = 20.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun SelectedSongComponent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(WepliTheme.color.gray000)
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(WepliTheme.color.gray150)
                .size(150.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_plus_gradient),
                tint = Color.Unspecified,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "노래를 선택해주세요",
            style = WepliTheme.typo.subTitle5,
            color = WepliTheme.color.gray900
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "선택하면 화면에 표시됩니다",
            style = WepliTheme.typo.body6,
            color = WepliTheme.color.gray500
        )
    }
}