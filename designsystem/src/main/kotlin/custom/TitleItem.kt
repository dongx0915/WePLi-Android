package custom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
fun TitleItemPreview() {
    Column {
        OneLineTitle(title = "위플리 인기 랭킹")
        Spacer(modifier = Modifier.height(12.dp))
        TwoLineTitle(title = "위플리 인기 랭킹", subscription = "위플리 차트에서 인기가 많은 가수들을 모아봤어요")
    }
}

@Composable
fun OneLineTitle(
    title: String,
    showIcon: Boolean = true,
) {
    Row(
        modifier = Modifier.padding(vertical = 12.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = WePLiTheme.typo.subTitle2,
            color = WePLiTheme.color.gray900,
        )
        if (showIcon) {
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_arrow_forward),
                tint = WePLiTheme.color.gray600,
                contentDescription = null
            )
        }
    }
}

@Composable
fun TwoLineTitle(
    modifier: Modifier = Modifier.padding(vertical = 12.dp, horizontal = 20.dp),
    title: String?,
    subscription: String?,
) {
    Column(modifier = modifier) {
        title?.let {
            Text(
                text = it,
                style = WePLiTheme.typo.subTitle2,
                color = WePLiTheme.color.gray900,
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        subscription?.let {
            Text(
                text = it,
                style = WePLiTheme.typo.body6,
                color = WePLiTheme.color.gray600,
            )
        }
    }
}