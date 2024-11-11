package com.wepli.mypage.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wepli.designsystem.R
import theme.WePLiTheme

@Preview
@Composable
fun MenuTitleComponent(
    modifier: Modifier = Modifier,
    title: String = "메뉴 제목"
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 24.dp, bottom = 8.dp)
    ) {
        Text(
            text = title,
            style = WePLiTheme.typo.subTitle1,
            color = WePLiTheme.color.gray900
        )
    }
}

@Preview
@Composable
fun MenuComponent(
    modifier: Modifier = Modifier,
    title: String = "메뉴 콘텐츠",
    onClickMenu: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 20.dp)
            .clickable { onClickMenu() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = WePLiTheme.typo.body3,
            color = WePLiTheme.color.gray600
        )

        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(id = R.drawable.ic_arrow_forward),
            tint = WePLiTheme.color.gray600,
            contentDescription = null
        )
    }
}