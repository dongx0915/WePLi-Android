package com.wepli.devmode.fcm.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wepli.devmode.fcm.R
import com.wepli.devmode.fcm.domain.model.FcmPriority
import com.wepli.devmode.fcm.presentation.component.ExpandableMenuComponent
import com.wepli.devmode.fcm.presentation.component.NoticeComponent
import com.wepli.devmode.fcm.presentation.component.PrioritySelectBottomSheet
import com.wepli.devmode.fcm.presentation.component.common.AppBarIcon
import com.wepli.devmode.fcm.presentation.textfield.DevModeTextField
import com.wepli.devmode.fcm.presentation.textfield.DevModeTextFieldType
import com.wepli.devmode.fcm.presentation.textfield.FieldLabel
import com.wepli.devmode.fcm.presentation.theme.DevModeTheme
import component.bottomsheet.WepliBottomSheet
import component.bottomsheet.WepliBottomSheetType
import org.orbitmvi.orbit.compose.collectAsState


@Preview
@Composable
fun DevFcmPushScreenPreview() {
    DevFcmPushScreen(DevFcmPushState(), {}, {})
}

@Composable
fun DevFcmPushScreenRoute(
    navOnBack: () -> Unit
) {
    val viewModel: DevFcmPushViewModel = hiltViewModel()
    val state: DevFcmPushState by viewModel.collectAsState()

    DevFcmPushScreen(
        state = state,
        sendAction = viewModel::processIntent,
        navOnBack = navOnBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DevFcmPushScreen(
    state: DevFcmPushState,
    sendAction: (DevFcmPushIntent) -> Unit,
    navOnBack: () -> Unit
) {
    Scaffold(
        containerColor = DevModeTheme.color.black,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "FCM 푸시 발송",
                        style = DevModeTheme.typo.subTitle3,
                        color = DevModeTheme.color.white,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                navigationIcon = {
                    AppBarIcon(
                        iconResource = R.drawable.ic_arrow_back,
                        onClick = { navOnBack() }
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = DevModeTheme.color.black,
                    navigationIconContentColor = DevModeTheme.color.white,
                    actionIconContentColor = DevModeTheme.color.white,
                    titleContentColor = DevModeTheme.color.white
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            NoticeComponent(
                notice = "API Key 파일이 로드 되었습니다.",
                leadingIcon = ImageVector.vectorResource(R.drawable.ic_file_check),
                trailingIcon = ImageVector.vectorResource(R.drawable.ic_close),
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            NotificationFieldLayout()

            PriorityFieldLayout(
                priority = state.priority,
                sendAction = sendAction,
                modifier = Modifier.padding(horizontal = 20.dp),
            )

            if (state.isShownPriorityBottomSheet) {
                PrioritySelectBottomSheet(
                    currentPriority = state.priority,
                    sendAction = sendAction
                )
            }
        }
    }
}

@Preview
@Composable
private fun NotificationFieldLayout() {
    ExpandableMenuComponent(
        title = "Notification 속성 설정",
        modifier = Modifier.padding(horizontal = 16.dp),
    ) {
        Column {
            Text(
                text = "해당 정보는 Notification 속성에 포함됩니다.",
                style = DevModeTheme.typo.body5,
                color = DevModeTheme.color.white
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Notification 속성이 포함되면 앱 내 서비스로 푸시 알림이 수신 되지 않습니다.",
                style = DevModeTheme.typo.body6,
                color = DevModeTheme.color.gray600
            )

            Spacer(modifier = Modifier.height(20.dp))

            NotificationInputLayout(
                labelTitle = "제목",
                isRequired = false,
                query = "",
                placeholder = "제목을 입력해주세요",
                onQueryUpdate = {},
                onEnter = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            NotificationInputLayout(
                labelTitle = "내용",
                isRequired = false,
                query = "",
                placeholder = "내용을 입력해주세요",
                onQueryUpdate = {},
                onEnter = {}
            )
        }
    }
}

@Composable
private fun NotificationInputLayout(
    labelTitle: String,
    isRequired: Boolean,
    query: String,
    placeholder: String,
    onQueryUpdate: (String) -> Unit,
    onEnter: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current


    FieldLabel(
        text = labelTitle,
        label = if (isRequired) "" else "선택",
        isRequired = isRequired,
    )

    Spacer(modifier = Modifier.height(8.dp))
    DevModeTextField(
        value = query,
        singleLine = true,
        onValueChanged = { onQueryUpdate(it) },
        onEnter = {
            onEnter()
            keyboardController?.hide()
        },
        placeholder = placeholder,
        type = DevModeTextFieldType.Normal,
        modifier = Modifier
            .focusRequester(focusRequester)
            .height(44.dp)
    )
}


@Composable
private fun PriorityFieldLayout(
    priority: FcmPriority,
    sendAction: (DevFcmPushIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FieldLabel(
            text = "푸시 우선순위",
            label = "*",
            isRequired = true
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { sendAction(DevFcmPushIntent.ShowPriorityBottomSheet(true)) }
                .clip(RoundedCornerShape(4.dp))
                .background(color = DevModeTheme.color.gray000)
                .padding(horizontal = 16.dp)
                .height(44.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = priority.value,
                    style = DevModeTheme.typo.subTitle5,
                    color = DevModeTheme.color.gray700,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_down_vector),
                    tint = Color.Unspecified,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}