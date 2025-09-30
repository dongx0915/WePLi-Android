package com.wepli.devmode.fcm.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wepli.devmode.fcm.presentation.component.common.AppBarIcon
import com.wepli.devmode.fcm.presentation.textfield.DevModeTextField
import com.wepli.devmode.fcm.presentation.textfield.DevModeTextFieldType
import com.wepli.devmode.fcm.presentation.theme.DevModeTheme
import com.wepli.devmode.fcm.R
import com.wepli.devmode.fcm.domain.model.FcmMessage
import com.wepli.devmode.fcm.presentation.component.NoticeComponent


@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DevFcmPushScreen() {
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
                        onClick = {}
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
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            NoticeComponent(
                notice = "",
                imageVector = ImageVector.vectorResource(com.wepli.core.resources.R.drawable.ic_info_vector)
            )
        }
    }
}