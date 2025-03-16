package com.wepli.search.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wepli.designsystem.R
import com.wepli.shared.feature.mock.songUiMockData
import com.wepli.uimodel.music.SongUiData
import component.bottomsheet.BottomSheetItem
import extensions.compose.toPx
import image.AsyncImageWithPreview
import theme.WepliTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongInfoBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    ),
    onClosed: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = { onClosed() },
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        containerColor = Color.Transparent,
        dragHandle = null,
        modifier = Modifier.navigationBarsPadding(),
    ) {
        Column(modifier = Modifier.padding(bottom = 16.dp)) {
            content()
        }
    }
}

@Composable
fun SongInfoBottomSheetContent(song: SongUiData) {
    val imageSize = 64.dp

    Column(modifier = Modifier.fillMaxHeight().padding(top = 62.dp)) {
        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier.padding(vertical = 12.dp, horizontal = 20.dp)) {
            AsyncImageWithPreview(
                imageUrl = song.getImageUrl(imageSize.toPx()),
                previewImage = painterResource(R.drawable.img_placeholder_chuu_2),
                imageOverrideSize = imageSize,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(4.dp)),
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = song.title,
                    style = WepliTheme.typo.subTitle3,
                    color = WepliTheme.color.gray900,
                    maxLines = 1,
                )

                Text(
                    text = song.artistName,
                    style = WepliTheme.typo.body4,
                    color = WepliTheme.color.gray600,
                    maxLines = 1,
                )
            }

            Icon(
                modifier = Modifier.size(24.dp).align(Alignment.CenterVertically),
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_forward_vector),
                contentDescription = null,
                tint = WepliTheme.color.gray900
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        BottomSheetItem(
            iconRes = R.drawable.ic_heart_vector,
            text = "좋아요",
            onClick = { }
        )

        BottomSheetItem(
            iconRes = R.drawable.ic_lylics_vector,
            text = "가사 보기",
            onClick = { }
        )

        BottomSheetItem(
            iconRes = R.drawable.ic_folder_vector,
            text = "플레이리스트에 추가하기",
            onClick = { }
        )

        BottomSheetItem(
            iconRes = R.drawable.ic_pencil_vector,
            text = "게시글로 공유하기",
            onClick = { }
        )

        BottomSheetItem(
            iconRes = R.drawable.ic_instagram_vector,
            text = "인스타그램으로 공유하기",
            onClick = { }
        )

        BottomSheetItem(
            iconRes = R.drawable.ic_kakao_vector,
            text = "카카오톡으로 공유하기",
            onClick = { }
        )

        BottomSheetItem(
            iconRes = R.drawable.ic_link_vector,
            text = "링크로 공유하기",
            onClick = { }
        )

        BottomSheetItem(
            iconRes = R.drawable.ic_download_vector,
            text = "스크린샷으로 저장",
            onClick = { }
        )

        BottomSheetItem(
            iconRes = R.drawable.ic_profile_vector,
            text = "아티스트 정보",
            onClick = { }
        )

        BottomSheetItem(
            iconRes = R.drawable.ic_info_vector,
            text = "곡 정보",
            onClick = { }
        )

        Spacer(modifier = Modifier.weight(2f))
    }
}


@Preview
@Composable
fun SongInfoBottomSheetContentPreview() {
    SongInfoBottomSheetContent(song = songUiMockData.random())
}