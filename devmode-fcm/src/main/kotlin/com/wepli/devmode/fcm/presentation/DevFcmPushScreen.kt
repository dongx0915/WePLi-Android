package com.wepli.devmode.fcm.presentation

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wepli.devmode.fcm.R
import com.wepli.devmode.fcm.domain.model.FcmPriority
import com.wepli.devmode.fcm.core.ui.ExpandableMenuComponent
import com.wepli.devmode.fcm.core.ui.LimitedSwipeToDismissBox
import com.wepli.devmode.fcm.presentation.component.NoticeComponent
import com.wepli.devmode.fcm.presentation.component.PrioritySelectBottomSheet
import com.wepli.devmode.fcm.core.ui.common.AppBarIcon
import com.wepli.devmode.fcm.core.ui.common.DevModeBasicButton
import com.wepli.devmode.fcm.core.ui.common.DevModeButtonStyle
import com.wepli.devmode.fcm.presentation.textfield.DevModeTextField
import com.wepli.devmode.fcm.presentation.textfield.DevModeTextFieldType
import com.wepli.devmode.fcm.presentation.textfield.FieldLabel
import com.wepli.devmode.fcm.presentation.theme.DevModeTheme
import com.wepli.devmode.fcm.core.ui.common.FullScreenLoader
import com.wepli.devmode.fcm.core.ui.extensions.dashedBorder
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect


@Preview
@Composable
fun DevFcmPushScreenPreview() {
    DevFcmPushScreen(DevFcmPushState(), {}, {})
}

@Composable
fun DevFcmPushScreenRoute(
    projectId: String,
    navOnBack: () -> Unit
) {
    val viewModel: DevFcmPushViewModel = hiltViewModel()
    val state: DevFcmPushState by viewModel.collectAsState()

    val context = LocalContext.current
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val jsonContent = context.contentResolver.openInputStream(it)
                ?.bufferedReader()
                ?.use { reader -> reader.readText() }

            jsonContent?.let { json ->
                viewModel.processIntent(DevFcmPushIntent.UploadJsonFile(json))
            }
        }
    }

    LaunchedEffect(projectId) {
        viewModel.processIntent(DevFcmPushIntent.Init(projectId))
    }

    viewModel.collectSideEffect {
        when (it) {
            DevFcmPushEffect.SendFcmSuccess -> {
                Toast.makeText(context, context.getString(R.string.dev_fcm_send_success), Toast.LENGTH_SHORT).show()
            }
            DevFcmPushEffect.ApiKeyValidError -> {
                Toast.makeText(context, context.getString(R.string.dev_fcm_auth_error), Toast.LENGTH_SHORT).show()
            }
            DevFcmPushEffect.FcmTokenValidError -> {
                Toast.makeText(context, context.getString(R.string.dev_fcm_token_error), Toast.LENGTH_SHORT).show()
            }
            DevFcmPushEffect.FcmTokenLoadFailed -> {
                Toast.makeText(context, context.getString(R.string.dev_fcm_token_load_failed), Toast.LENGTH_SHORT).show()
            }
            is DevFcmPushEffect.UnknownError -> {
                Toast.makeText(context, context.getString(R.string.dev_fcm_unknown_error, it.code), Toast.LENGTH_SHORT).show()
            }
        }
    }

    DevFcmPushScreen(
        state = state,
        sendAction = viewModel::processIntent,
        navOnBack = navOnBack,
        onFilePickerClick = { filePickerLauncher.launch("application/json") }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DevFcmPushScreen(
    state: DevFcmPushState,
    sendAction: (DevFcmPushIntent) -> Unit,
    navOnBack: () -> Unit,
    onFilePickerClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = DevModeTheme.color.black,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.dev_fcm_push_title),
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
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(modifier = Modifier.height(28.dp))

            ApiKeyFileLayout(state = state, sendAction = sendAction, onFilePickerClick = onFilePickerClick)

            NotificationFieldLayout(sendAction = sendAction)

            PriorityFieldLayout(
                priority = state.priority,
                sendAction = sendAction,
                modifier = Modifier.padding(horizontal = 20.dp),
            )

            PushDataFieldLayout(
                state = state,
                sendAction = sendAction,
                modifier = Modifier.padding(horizontal = 20.dp),
            )

            Spacer(
                modifier = Modifier
                    .height(48.dp)
                    .weight(1f)
            )

            DevModeBasicButton(
                title = stringResource(R.string.dev_fcm_push_send_button),
                isEnabled = true,
                onClick = { sendAction(DevFcmPushIntent.SendFcm) },
                buttonStyle = DevModeButtonStyle.Basic,
                modifier = Modifier
                    .background(DevModeTheme.color.black)
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 20.dp)
            )


            if (state.isShownPriorityBottomSheet) {
                PrioritySelectBottomSheet(
                    currentPriority = state.priority,
                    sendAction = sendAction
                )
            }

            if (state.isLoading) {
                FullScreenLoader()
            }
        }
    }
}


@Composable
private fun ApiKeyFileLayout(
    state: DevFcmPushState,
    sendAction: (DevFcmPushIntent) -> Unit,
    onFilePickerClick: () -> Unit,
) {
    if (state.isJsonFileLoaded) {
        NoticeComponent(
            notice = stringResource(R.string.dev_fcm_api_key_loaded),
            leadingIcon = ImageVector.vectorResource(R.drawable.ic_file_check),
            trailingIcon = ImageVector.vectorResource(R.drawable.ic_close),
            onClickTrailingIcon = {
                sendAction(DevFcmPushIntent.DeleteJsonFile)
            },
            modifier = Modifier
                .padding(horizontal = 20.dp)
        )
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onFilePickerClick()
                }
                .padding(horizontal = 20.dp)
                .dashedBorder(
                    color = DevModeTheme.color.gray300,
                    shape = RoundedCornerShape(8.dp),
                    strokeWidth = 1.dp,
                    dashLength = 2.dp,
                )
                .padding(vertical = 20.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_file_up),
                tint = Color.Unspecified,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(R.string.dev_fcm_api_key_upload_guide),
                style = DevModeTheme.typo.body6,
                color = DevModeTheme.color.white,
            )
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.dev_fcm_file_select),
                style = DevModeTheme.typo.body6,
                color = DevModeTheme.color.white,
                modifier = Modifier
                    .background(
                        color = DevModeTheme.color.gray100,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}

@Composable
private fun NotificationFieldLayout(
    sendAction: (DevFcmPushIntent) -> Unit,
) {
    ExpandableMenuComponent(
        title = stringResource(R.string.dev_fcm_notification_title),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .border(
                width = 1.dp,
                color = DevModeTheme.color.gray050,
                shape = RoundedCornerShape(8.dp)
            ),
    ) {
        Column {
            Text(
                text = stringResource(R.string.dev_fcm_notification_description),
                style = DevModeTheme.typo.body5,
                color = DevModeTheme.color.white
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.dev_fcm_notification_warning),
                style = DevModeTheme.typo.body6,
                color = DevModeTheme.color.gray600
            )

            Spacer(modifier = Modifier.height(20.dp))

            NotificationInputLayout(
                labelTitle = stringResource(R.string.dev_fcm_notification_field_title),
                isRequired = false,
                query = "",
                placeholder = stringResource(R.string.dev_fcm_notification_placeholder_title),
                onQueryUpdate = {
                    sendAction(DevFcmPushIntent.UpdateTitle(it))
                },
                onEnter = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            NotificationInputLayout(
                labelTitle = stringResource(R.string.dev_fcm_notification_field_content),
                isRequired = false,
                query = "",
                placeholder = stringResource(R.string.dev_fcm_notification_placeholder_content),
                onQueryUpdate = {
                    sendAction(DevFcmPushIntent.UpdateDescription(it))
                },
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
        label = if (isRequired) "" else stringResource(R.string.dev_fcm_field_optional),
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
            text = stringResource(R.string.dev_fcm_priority_title),
            label = stringResource(R.string.dev_fcm_field_required),
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

@Composable
private fun PushDataFieldLayout(
    state: DevFcmPushState,
    sendAction: (DevFcmPushIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FieldLabel(
            text = stringResource(R.string.dev_fcm_push_data_title),
            label = stringResource(R.string.dev_fcm_field_required),
            isRequired = true
        )

        state.pushDataItems.forEachIndexed { index, item ->
            PushDataInputLayout(
                index = index,
                keyQuery = item.first,
                onKeyQueryUpdate = { index, key ->
                    sendAction(DevFcmPushIntent.UpdatePushDataKey(index, key))
                },
                valueQuery = item.second,
                onValueQueryUpdate = { index, value ->
                    sendAction(DevFcmPushIntent.UpdatePushDataValue(index, value))
                },
                onRemove = {
                    sendAction(DevFcmPushIntent.RemovePushDataItem(index))
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        DevModeBasicButton(
            title = stringResource(R.string.dev_fcm_push_data_add_button),
            isEnabled = true,
            onClick = { sendAction(DevFcmPushIntent.AddPushDataItem) },
            buttonStyle = DevModeButtonStyle.Transparent(),
        )
    }
}

@Composable
private fun PushDataInputLayout(
    index: Int,
    keyQuery: String,
    onKeyQueryUpdate: (Int, String) -> Unit,
    valueQuery: String,
    onValueQueryUpdate: (Int, String) -> Unit,
    onRemove: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }

    LimitedSwipeToDismissBox(
        maxOffsetDp = 50f.dp,
        positionalThresholdFraction = 0.25f,
        backgroundContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.Center)
                    .clickable {
                        onRemove(index)
                    }
            ) {
                Spacer(modifier.weight(1f))
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_trash),
                    tint = DevModeTheme.color.gray900,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
            }
        },
        content = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DevModeTextField(
                    value = keyQuery,
                    singleLine = true,
                    onValueChanged = { onKeyQueryUpdate(index, it) },
                    onEnter = {},
                    placeholder = stringResource(R.string.dev_fcm_push_data_key_placeholder),
                    type = DevModeTextFieldType.Normal,
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .height(44.dp)
                        .weight(1f)
                )

                DevModeTextField(
                    value = valueQuery,
                    singleLine = true,
                    onValueChanged = { onValueQueryUpdate(index, it) },
                    onEnter = {},
                    placeholder = stringResource(R.string.dev_fcm_push_data_value_placeholder),
                    type = DevModeTextFieldType.Normal,
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .height(44.dp)
                        .weight(1f)
                )
            }
        }
    )
}