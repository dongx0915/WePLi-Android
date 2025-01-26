package component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import theme.WepliTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WepliBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    ),
    type: WepliBottomSheetType,
    onClosed: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = { onClosed() },
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        containerColor = WepliTheme.color.gray050,
        dragHandle = null,
        modifier = Modifier.navigationBarsPadding(),
    ) {
        Column(modifier = Modifier.padding(bottom = 16.dp)) {
            BottomSheetHeader(type)
            content()
        }
    }
}

@Composable
private fun BottomSheetHeader(
    type: WepliBottomSheetType,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .width(28.dp)
                .height(3.dp)
                .background(color = WepliTheme.color.gray300)
                .align(Alignment.CenterHorizontally)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
        ) {
            Text(
                text = type.title,
                style = WepliTheme.typo.subTitle1,
                color = WepliTheme.color.gray900,
                modifier = Modifier.align(Alignment.Center)
            )

            when (type) {
                is WepliBottomSheetType.Button -> {
                    BottomSheetTextButton(
                        buttonText = type.text,
                        onClick = type.onClick,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
                else -> Unit
            }
        }
    }
}

@Composable
private fun BottomSheetTextButton(
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .fillMaxHeight()
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = buttonText,
            style = WepliTheme.typo.subTitle5.copy(
                brush = WepliTheme.color.linear3
            )
        )
    }
}

/**
 * Preview Example Content
 */
@Preview
@Composable
fun WepliBottomSheetPreview() {
    // ModalBottomSheet를 사용하면 Preview가 나오지 않으므로 Sample Preview로 대체
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        // Normal 타입
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                .background(color = WepliTheme.color.gray050)
                .padding(bottom = 16.dp)
        ) {
            BottomSheetHeader(type = WepliBottomSheetType.Normal("타이틀"))
            BottomSheetContentExample()
        }

        // Button 타입
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                .background(color = WepliTheme.color.gray050)
                .padding(bottom = 16.dp)
        ) {
            BottomSheetHeader(type = WepliBottomSheetType.Button(title = "타이틀", "완료") { })
            BottomSheetContentExample()
        }
    }
}

@Composable
fun BottomSheetContentExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        repeat(3) {
            Column(modifier = Modifier.padding(vertical = 16.dp)) {
                Text(
                    text = "노래 검색하기",
                    style = WepliTheme.typo.subTitle3,
                    color = WepliTheme.color.gray600
                )
            }
        }
    }
}

