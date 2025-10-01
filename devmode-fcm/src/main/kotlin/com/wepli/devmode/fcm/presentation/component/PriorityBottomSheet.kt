package com.wepli.devmode.fcm.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wepli.devmode.fcm.R
import com.wepli.devmode.fcm.domain.model.FcmPriority
import com.wepli.devmode.fcm.presentation.DevFcmPushIntent
import com.wepli.devmode.fcm.presentation.theme.DevModeTheme
import component.bottomsheet.WepliBottomSheet
import component.bottomsheet.WepliBottomSheetType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioritySelectBottomSheet(
    currentPriority: FcmPriority,
    sendAction: (DevFcmPushIntent) -> Unit
) {
    WepliBottomSheet(
        onClosed = { sendAction(DevFcmPushIntent.ShowPriorityBottomSheet(false)) },
        type = WepliBottomSheetType.Normal(
            title = "푸시 우선순위"
        ),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            FcmPriority.entries.forEach {
                PriorityItem(
                    priority = it,
                    description = "",
                    isChecked = currentPriority == it,
                    sendAction = sendAction
                )
            }
        }
    }
}

@Composable
fun PriorityItem(
    priority: FcmPriority,
    description: String,
    isChecked: Boolean,
    sendAction: (DevFcmPushIntent) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                sendAction(DevFcmPushIntent.UpdatePriority(priority = priority))
                sendAction(DevFcmPushIntent.ShowPriorityBottomSheet(false))
            }
            .padding(vertical = 6.dp, horizontal = 20.dp),
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = priority.value,
                style = DevModeTheme.typo.subTitle3,
                color = DevModeTheme.color.gray700
            )

            Text(
                text = description,
                style = DevModeTheme.typo.body6,
                color = DevModeTheme.color.gray500
            )
        }

        if (isChecked) {
            Icon(
                painter = painterResource(R.drawable.ic_checkbox),
                tint = Color.Unspecified,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}