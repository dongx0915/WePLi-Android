package com.wepli.devmode.fcm.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.wepli.devmode.fcm.presentation.component.AppBarIcon
import com.wepli.devmode.fcm.R


@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DevFcmPushScreen() {
    Scaffold(
        containerColor = Color.Black,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "FCM 푸시 발송",
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                        ),
                        color = Color.White,
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
                    containerColor = Color.Black,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

        }
    }
}