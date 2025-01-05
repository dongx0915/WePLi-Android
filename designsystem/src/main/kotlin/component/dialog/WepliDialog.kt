package component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import theme.WePLiTheme
import theme.WepliTheme

@Composable
fun WepliDialog(
    title: String = "",
    subTitle: String = "",
    dialogType: WepliDialogType,
    onDismissRequest: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false,)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(color = WepliTheme.color.gray050)
                .padding(top = 32.dp, bottom = 20.dp, start = 24.dp, end = 24.dp)
        ) {
            DialogContent(title = title, subTitle = subTitle)

            when (dialogType) {
                is WepliDialogType.TwoButton -> TwoButtonLayout(dialogType = dialogType)
                is WepliDialogType.OneButton -> OneButtonLayout(dialogType = dialogType)
            }
        }
    }
}

@Composable
private fun DialogContent(
    title: String = "",
    subTitle: String = "",
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (title.isNotBlank()) {
            Text(
                text = title,
                style = WepliTheme.typo.title3,
                color = WepliTheme.color.white,
            )
        }

        if (subTitle.isNotBlank()) {
            Text(
                text = subTitle,
                style = WepliTheme.typo.body4,
                color = WepliTheme.color.gray600,
            )
        }
    }
}

@Composable
private fun TwoButtonLayout(dialogType: WepliDialogType.TwoButton) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.End),
    ) {
        DialogButton(
            buttonText = dialogType.cancelButtonText,
            isPositive = false,
            onClick = dialogType.cancelButtonClick,
        )

        DialogButton(
            buttonText = dialogType.okButtonText,
            isPositive = true,
            onClick = dialogType.okButtonClick,
        )
    }
}

@Composable
private fun OneButtonLayout(dialogType: WepliDialogType.OneButton) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.End,
    ) {
        DialogButton(
            buttonText = dialogType.okButtonText,
            isPositive = true,
            onClick = dialogType.okButtonClick,
        )
    }
}

@Composable
private fun DialogButton(
    buttonText: String,
    isPositive: Boolean,
    onClick: () -> Unit,
) {
    val buttonStyle = if (isPositive) {
        WepliTheme.typo.subTitle5.copy(
            brush = WepliTheme.color.linear3
        )
    } else {
        WepliTheme.typo.subTitle5.copy(
            color = WepliTheme.color.gray600
        )
    }

    Box(
        modifier = Modifier
            .height(44.dp)
            .padding(horizontal = 20.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = buttonText,
            style = buttonStyle
        )
    }
}

@Preview
@Composable
private fun WepliOneButtonDialogPreview() {
    WePLiTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            WepliDialog(
                title = "다이얼로그 타이틀",
                subTitle = "바디 카피 영역",
                dialogType = WepliDialogType.OneButton(
                    okButtonText = "확인",
                    okButtonClick = {},
                )
            )
        }
    }
}

@Preview
@Composable
private fun WepliTwoButtonDialogPreview() {
    WePLiTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            WepliDialog(
                title = "다이얼로그 타이틀",
                subTitle = "바디 카피 영역",
                dialogType = WepliDialogType.TwoButton(
                    okButtonText = "확인",
                    cancelButtonText = "취소",
                    okButtonClick = {},
                    cancelButtonClick = {},
                )
            )
        }
    }
}

@Preview
@Composable
private fun ButtonPreview() {
    Row {
        DialogButton(
            buttonText = "취소",
            isPositive = false,
            onClick = {}
        )

        DialogButton(
            buttonText = "확인",
            isPositive = true,
            onClick = {}
        )
    }
}