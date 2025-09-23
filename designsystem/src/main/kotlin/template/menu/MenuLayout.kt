package template.menu

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
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
import base.Intent
import component.switch.WepliSwitch
import theme.WepliTheme
import com.wepli.core.resources.R as CoreR

@Composable
fun MenuLayout(
    sections: List<MenuSection>,
    onAction: (Intent) -> Unit
) {
    Column {
        sections.forEach { section ->
            MenuTitleComponent(title = section.title)
            section.items.forEach { item ->
                MenuComponent(title = item.title, onClickMenu = { onAction(item.intent) })
            }
        }
    }
}

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
            style = WepliTheme.typo.subTitle1,
            color = WepliTheme.color.gray900
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
            .clickable { onClickMenu() }
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = WepliTheme.typo.body3,
            color = WepliTheme.color.gray600
        )

        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(id = CoreR.drawable.ic_arrow_forward),
            tint = WepliTheme.color.gray600,
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun SwitchMenuComponent(
    modifier: Modifier = Modifier,
    title: String = "메뉴 콘텐츠",
    checked: Boolean = true,
    onClickMenu: () -> Unit = {},
    onCheckedChanged: (Boolean) -> Unit = {},
) {
    Row(
        modifier = modifier
            .clickable { onClickMenu() }
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = WepliTheme.typo.body3,
            color = WepliTheme.color.gray600
        )

        WepliSwitch(
            checked = checked,
            onCheckedChange = onCheckedChanged,
        )
    }
}

@Preview
@Composable
fun ExpandableMenuComponent(
    modifier: Modifier = Modifier,
    title: String = "메뉴 콘텐츠",
    content: String = "콘텐츠"
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
                style = WepliTheme.typo.body3,
                color = WepliTheme.color.gray600
            )

            Icon(
                modifier = Modifier
                    .size(16.dp)
                    .rotate(rotationAngle),
                painter = painterResource(id = CoreR.drawable.ic_arrow_forward),
                tint = WepliTheme.color.gray600,
                contentDescription = null
            )
        }

        if (expanded) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier,
                text = content,
                style = WepliTheme.typo.body5,
                color = WepliTheme.color.white
            )
        }
    }
}