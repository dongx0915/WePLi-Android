package com.wepli.devmode.fcm.presentation.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wepli.devmode.fcm.presentation.theme.DevModeTheme
import com.wepli.devmode.fcm.R

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun ExpandableMenuComponent(
    modifier: Modifier = Modifier,
    title: String = "메뉴 콘텐츠",
    innerContent: @Composable () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (expanded) 270f else 90f,
        label = "ArrowRotationAngle"
    )

    Column(
        modifier = modifier
            .animateContentSize()
            .clickable {
                expanded = !expanded
            }
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 20.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.weight(1f),
                text = title,
                style = DevModeTheme.typo.body3,
                color = DevModeTheme.color.gray600
            )

            Icon(
                modifier = Modifier
                    .size(16.dp)
                    .rotate(rotationAngle),
                painter = painterResource(id = R.drawable.ic_arrow_forward),
                tint = DevModeTheme.color.gray600,
                contentDescription = null
            )
        }

        if (expanded) {
            Spacer(modifier = Modifier.height(16.dp))
            innerContent()
        }
    }
}