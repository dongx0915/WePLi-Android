package com.wepli.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LazyScrollableAppBar() {
    val scrollState = rememberLazyListState()
    custom.ScrollableAppBar(
        scrollState = scrollState,
        startBgColor = Color.Transparent,
        endBgColor = Color.Black,
        content = { paddingValue ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValue),
                state = scrollState
            ) {
                items(100) {
                    Text(
                        text = "Item $it",
                        color = Color.Black,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun ScrollableAppBar() {
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
                    Text(
                        text = "Item $it",
                        color = Color.Black,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun LazyScrollableAppBarPreview() {
    LazyScrollableAppBar()
}

@Preview
@Composable
fun ScrollableAppBarPreview() {
    ScrollableAppBar()
}