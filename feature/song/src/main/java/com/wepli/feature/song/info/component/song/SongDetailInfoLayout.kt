package com.wepli.feature.song.info.component.song

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import custom.OneLineTitle
import theme.WepliTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun SongDetailInfoLayout(
    composers: List<String>,
    genres: List<String>
) {
    @Composable
    fun TagList(title: String, items: List<String>, modifier: Modifier = Modifier) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = title,
                style = WepliTheme.typo.body4,
                color = WepliTheme.color.gray700,
                modifier = Modifier
                    .width(40.dp)
                    .padding(vertical = 8.dp)
                    .align(Alignment.Top)
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items.forEach {
                    Text(
                        text = it,
                        style = WepliTheme.typo.body5,
                        color = WepliTheme.color.gray700,
                        modifier = Modifier
                            .border(1.dp, WepliTheme.color.gray100, RoundedCornerShape(100.dp))
                            .padding(vertical = 8.dp, horizontal = 12.dp)
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        OneLineTitle(
            title = "곡 정보",
            showIcon = true,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        Column(
            modifier = Modifier.padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            if (composers.isNotEmpty()) {
                TagList("작곡", composers)
            }

            if (genres.isNotEmpty()) {
                TagList("장르", genres)
            }
        }
    }
}