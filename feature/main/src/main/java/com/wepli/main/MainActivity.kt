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

        enableEdgeToEdge()
        setContent {
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
