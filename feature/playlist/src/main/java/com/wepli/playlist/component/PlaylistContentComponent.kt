package com.wepli.playlist.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wepli.designsystem.R
import common.ExpandableText
import org.joda.time.LocalDate
import theme.WePLiTheme

@Composable
fun PlaylistContentHeader(title: String, author: String) {
    Row {
        Image(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(4.dp)),
            painter = painterResource(id = R.drawable.img_placeholder_eunbin),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = title,
                style = WePLiTheme.typo.subTitle2,
                color = WePLiTheme.color.white,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = author,
                style = WePLiTheme.typo.body6,
                color = WePLiTheme.color.gray700,
            )
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun PlaylistContentBody(
    description: String,
    bSideTrackCount: Int,
    createdAt: LocalDate,
) {
    val createData = remember { createdAt.toString("yyyy.MM.dd") }
    val spanStyle = SpanStyle(
        fontStyle = WePLiTheme.typo.caption2.fontStyle,
        color = WePLiTheme.color.gray600,
    )

    Text(
        text = "$createData • ${bSideTrackCount}곡 • 1시간 34분",
        style = WePLiTheme.typo.body5,
        color = WePLiTheme.color.gray600,
    )

    Spacer(modifier = Modifier.height(4.dp))

    ExpandableText(
        text = description,
        style = WePLiTheme.typo.body6.copy(
            color = WePLiTheme.color.gray500,
        ),
        collapsedMaxLine = 2,
        showMoreText = " ...더보기",
        showLessText = " 닫기",
        showMoreStyle = spanStyle,
        showLessStyle = spanStyle,
    )
}

@Preview
@Composable
fun PlaylistContentFooter() {
    Row {
        PlaylistButton(
            modifier = Modifier
                .weight(1f)
                .height(44.dp),
            buttonText = "PLAY",
            buttonIcon = painterResource(id = R.drawable.ic_play_filled),
        )

        Spacer(modifier = Modifier.width(8.dp))

        PlaylistButton(
            modifier = Modifier
                .weight(1f)
                .height(44.dp),
            buttonText = "SHUFFLE",
            buttonIcon = painterResource(id = R.drawable.ic_shuffle),
        )
    }
}

@Composable
fun PlaylistButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    buttonIcon: Painter,
    onClick: () -> Unit = {},
) {
    Button(
        modifier = modifier,
        onClick = { onClick.invoke() },
        colors = ButtonDefaults.buttonColors(
            containerColor = WePLiTheme.color.white.copy(alpha = 0.1f),
            contentColor = WePLiTheme.color.white,
        ),
        shape = RoundedCornerShape(4.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = buttonIcon,
                tint = WePLiTheme.color.white,
                contentDescription = "",
            )

            Text(
                text = buttonText,
                style = WePLiTheme.typo.body1,
                color = WePLiTheme.color.white,
            )

            Spacer(modifier = Modifier.width(7.dp))
        }
    }
}