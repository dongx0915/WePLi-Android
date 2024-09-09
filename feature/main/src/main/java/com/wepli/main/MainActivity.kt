package com.wepli.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import custom.ScrollableAppBar
import theme.WePLiTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // enableEdgeToEdge는 시스템 창을 엣지 투 엣지로 확장하여 더 몰입감 있는 UI를 제공
        enableEdgeToEdge()

        // Jetpack Compose UI를 설정하는 부분
        setContent {
            // WePLiTheme은 앱의 테마를 적용하는 래퍼 함수
            WePLiTheme {
                val scrollState = rememberScrollState()
                ScrollableAppBar(
                    title = "WePLi",
                    scrollState = scrollState,
                    content = { paddingValue ->
                        Column(
                            modifier = Modifier
                                .padding(paddingValue)
                                .fillMaxSize()
                                .verticalScroll(scrollState)
                        ) {
                            repeat(100) {
                                Text("Item $it", color = Color.Black)
                            }
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScrollableAppBarPreview() {
    val scrollState = rememberScrollState()
    custom.ScrollableAppBar(
        title = "WePLi",
        scrollState = scrollState,
        content = { paddingValue ->
            Column(
                modifier = Modifier
                    .padding(paddingValue)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                repeat(100) {
                    Text("Item $it", color = Color.Black)
                }
            }
        }
    )
}
