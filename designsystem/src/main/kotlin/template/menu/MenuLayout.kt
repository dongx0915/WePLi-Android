package template.menu

import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import base.Intent
import component.switch.WepliSwitch
import kotlinx.coroutines.launch
import theme.WepliTheme
import kotlin.math.roundToInt
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
fun MenuSwitchComponent(
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

        WepliSwitch(
            checked = true,
            onCheckedChange = {},
        )
    }
}