package com.wepli.community.write.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.WepliAppBar
import button.WepliBasicButton
import com.wepli.community.write.mvi.CommunityWriteIntent
import com.wepli.community.write.mvi.CommunityWriteUiState
import com.wepli.community.write.viewmodel.CommunityWriteViewModel
import com.wepli.designsystem.R
import com.wepli.uimodel.music.SongUiData
import component.bottomsheet.WepliBottomSheetType
import component.bottomsheet.WepliBottomSheet
import custom.SongItem
import org.orbitmvi.orbit.compose.collectAsState
import textfield.WepliTextField
import textfield.WepliTextFieldType
import theme.WepliTheme

@Composable
fun CommunityWriteScreenRoute(
    selectedSongs: List<SongUiData>?,
    navOnBack: () -> Unit,
    navOnSearchDetail: () -> Unit
) {
    val viewModel: CommunityWriteViewModel = hiltViewModel()
    val state: CommunityWriteUiState by viewModel.collectAsState()

    selectedSongs?.let {
        LaunchedEffect(it) {
            viewModel.processIntent(CommunityWriteIntent.UpdateSelectedSongs(selectedSongs))
        }
    }

    CommunityWriteScreen(
        state = state,
        navOnBack = navOnBack,
        navOnSearchDetail = navOnSearchDetail,
        sendAction = viewModel::processIntent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CommunityWriteScreen(
    state: CommunityWriteUiState,
    navOnBack: () -> Unit,
    navOnSearchDetail: () -> Unit,
    sendAction: (CommunityWriteIntent) -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            WepliAppBar(
                title = "게시글 작성",
                showBackButton = true,
                onClickBack = { navOnBack() }
            )
        },
        containerColor = WepliTheme.color.black
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(paddingValues)
                .padding(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            TitleLayout(
                title = state.title.text,
                isTitleLengthExceeded = state.title.isLengthExceeded,
                sendAction = sendAction,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            ContentsLayout(
                contents = state.contents.text,
                isContentsLengthExceeded = state.contents.isLengthExceeded,
                sendAction = sendAction,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            SelectedSongLayout(
                selectedSongs = state.selectedSongs,
                sendAction = sendAction
            )

            Spacer(modifier = Modifier.height(20.dp))

            WepliBasicButton(
                title = "작성 완료",
                isEnabled = true,
                onClick = { /* TODO */ },
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            // 노래 추가 방법 선택 바텀시트
            if (state.isShowMusicSelectBottomSheet) {
                MusicSourceSelectionBottomSheet(
                    sendAction = sendAction,
                    navOnSearchDetail = navOnSearchDetail
                )
            }
        }
    }
}

@Composable
fun TitleLayout(
    title: String,
    maxLength: Int = 25,
    isTitleLengthExceeded: Boolean,
    sendAction: (CommunityWriteIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FieldLabel("제목", "*", true)
        LimitedLengthTextField(
            value = title,
            maxLength = maxLength,
            isLengthExceeded = isTitleLengthExceeded,
            placeholder = "제목을 작성해주세요.",
            errorText = "제목은 ${maxLength}자 이내로 작성해주세요.",
            type = WepliTextFieldType.Normal,
            onValueChanged = { newValue, maxLength ->
                sendAction(CommunityWriteIntent.UpdateTitle(newValue, maxLength))
            }
        )
    }
}

@Composable
fun ContentsLayout(
    contents: String,
    maxLength: Int = 250,
    isContentsLengthExceeded: Boolean,
    sendAction: (CommunityWriteIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FieldLabel("내용", "*", true)

        LimitedLengthTextField(
            value = contents,
            maxLength = maxLength,
            isLengthExceeded = isContentsLengthExceeded,
            placeholder = "내용을 작성해주세요.",
            errorText = "내용은 ${maxLength}자 이내로 작성해주세요.",
            type = WepliTextFieldType.MultiLine,
            onValueChanged = { newValue, maxLength ->
                sendAction(CommunityWriteIntent.UpdateContents(newValue, maxLength))
            }
        )
    }
}

@Composable
fun SelectedSongLayout(
    modifier: Modifier = Modifier,
    selectedSongs: List<SongUiData>,
    sendAction: (CommunityWriteIntent) -> Unit,
) {
    val lazyRowState = rememberLazyListState()

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FieldLabel(
            text = "노래 추가하기",
            label = "선택",
            isRequired = false,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        LazyRow(
            state = lazyRowState,
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            items(selectedSongs) { song ->
                SongItem(song = song)
            }

            item {
                AddSongButton {
                    sendAction(CommunityWriteIntent.ShowMusicSelectBottomSheet(true))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicSourceSelectionBottomSheet(
    sendAction: (CommunityWriteIntent) -> Unit,
    navOnSearchDetail: () -> Unit,
) {
    @Composable
    fun BottomSheetItem(title: String, onClick: () -> Unit) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    sendAction(CommunityWriteIntent.ShowMusicSelectBottomSheet(false))
                    onClick()
                }
                .padding(vertical = 16.dp, horizontal = 20.dp)
        ) {
            Text(
                text = title,
                style = WepliTheme.typo.subTitle3,
                color = WepliTheme.color.gray600
            )
        }
    }

    WepliBottomSheet(
        onClosed = { sendAction(CommunityWriteIntent.ShowMusicSelectBottomSheet(false)) },
        type = WepliBottomSheetType.Normal(title = "노래를 어떻게 가져올까요?"),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            BottomSheetItem("노래 검색하기") { navOnSearchDetail() }
            BottomSheetItem("플레이리스트 가져오기") { /* TODO */ }
        }
    }
}

/** 버튼 **/
@Composable
fun AddSongButton(
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .size(92.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(WepliTheme.color.gray000),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_plus_gradient),
            tint = Color.Unspecified,
            contentDescription = null
        )
    }
}

/** -- **/

@Composable
fun FieldLabel(
    text: String,
    label: String,
    isRequired: Boolean,
    modifier: Modifier = Modifier,
) {
    val labelStyle = with(WepliTheme.typo) {
        if (isRequired) body4 else caption2
    }

    Row(
        modifier = modifier,
        verticalAlignment = if (isRequired) Alignment.Top else Alignment.CenterVertically,
        horizontalArrangement = if (isRequired) Arrangement.spacedBy(2.dp) else Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = text,
            style = WepliTheme.typo.body4,
            color = WepliTheme.color.gray900
        )
        Text(
            text = label,
            style = labelStyle.copy(
                brush = WepliTheme.color.linear3
            )
        )
    }
}

@Composable
fun LimitedLengthTextField(
    value: String,
    maxLength: Int,
    isLengthExceeded: Boolean,
    placeholder: String,
    errorText: String,
    type: WepliTextFieldType,
    onValueChanged: (String, Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        WepliTextField(
            value = value,
            onValueChanged = { newValue ->
                onValueChanged(newValue, maxLength)
            },
            isError = isLengthExceeded,
            singleLine = type == WepliTextFieldType.Normal,
            placeholder = placeholder,
            type = type
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (isLengthExceeded) {
                Text(
                    text = errorText,
                    style = WepliTheme.typo.body6,
                    color = WepliTheme.color.red500
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${value.length}/$maxLength",
                style = WepliTheme.typo.body6,
                color = WepliTheme.color.gray500
            )
        }
    }
}

@Preview
@Composable
fun CommunityWriteScreenPreview() {
    CommunityWriteScreen(
        state = CommunityWriteUiState(),
        navOnBack = {},
        navOnSearchDetail = {},
        sendAction = {}
    )
}