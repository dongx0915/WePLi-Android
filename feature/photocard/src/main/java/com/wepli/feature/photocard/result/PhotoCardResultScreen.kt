package com.wepli.feature.photocard.result

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.AppBarIcon
import appbar.AppBarIconType
import appbar.WepliAppBar
import button.WepliBasicButton
import button.WepliButtonStyle
import com.wepli.designsystem.R
import com.wepli.feature.photocard.component.PhotoCardComponent4
import com.wepli.feature.photocard.result.mvi.PhotoCardResultIntent
import com.wepli.feature.photocard.result.mvi.PhotoCardResultUiState
import com.wepli.shared.feature.mock.songMockData
import com.wepli.shared.feature.mock.userMockData
import com.wepli.shared.feature.uimodel.photocard.PhotoCardUiData
import component.bottomsheet.WepliBottomSheet
import component.bottomsheet.WepliBottomSheetType
import extensions.saveBitmapToFile
import extensions.toAndroidBitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.orbitmvi.orbit.compose.collectAsState
import theme.WepliTheme

@Composable
fun PhotoCardResultScreenRoute(
    photoCardInfo: PhotoCardUiData?,
    navOnBack: () -> Unit
) {
    val viewModel: PhotoCardResultViewModel = hiltViewModel()
    val state by viewModel.collectAsState()
    photoCardInfo?.let {
        LaunchedEffect(photoCardInfo) {
            viewModel.processIntent(PhotoCardResultIntent.Initialize(photoCardInfo))
        }
    }

    PhotoCardResultScreen(state, viewModel::processIntent, navOnBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@Composable
fun PhotoCardResultScreen(
    state: PhotoCardResultUiState,
    sendAction: (PhotoCardResultIntent) -> Unit,
    navOnBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val graphicsLayer = rememberGraphicsLayer()
    var photoCardBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    Scaffold(
        topBar = {
            WepliAppBar(
                showBackButton = true,
                onClickBack = navOnBack,
                actionIcons = listOf {
                    AppBarIcon(
                        icon = AppBarIconType.Save {
                            onClickSaveBtn(context, coroutineScope, graphicsLayer)
                        }
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(color = WepliTheme.color.black)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(paddingValues)
                .padding(top = 56.dp, start = 24.dp, end = 24.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "포토카드가 완성되었어요!",
                style = WepliTheme.typo.title1,
                color = WepliTheme.color.gray900
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "${state.user.nickname}님만의 포토카드가 완성 되었어요!\n친구들에게 공유해볼까요?",
                style = WepliTheme.typo.body4,
                color = WepliTheme.color.gray500,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.weight(4f))

            PhotoCardComponent4(
                photoCardInfo = state.photoCardInfo,
                isEnabledShimmer = false,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .drawWithContent {
                        graphicsLayer.record {
                            this@drawWithContent.drawContent()
                        }

                        drawLayer(graphicsLayer)
                    }
            )

            Spacer(modifier = Modifier.weight(3f))
            WepliBasicButton(
                title = "공유하기",
                isEnabled = true,
                onClick = {
                    coroutineScope.launch {
                        // 클릭한 시점의 비트맵을 가져오기 위해 클릭 시점에 변환
                        photoCardBitmap = capturePhotoCard(graphicsLayer)
                        sendAction(PhotoCardResultIntent.ShowShareBottomSheet(true))
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                buttonStyle = WepliButtonStyle.Basic,
            )
            Spacer(modifier = Modifier.height(8.dp))
            WepliBasicButton(
                title = "나가기",
                isEnabled = true,
                onClick = { navOnBack() },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                buttonStyle = WepliButtonStyle.Transparent,
            )

            if (state.isShownShareBottomSheet) {
                photoCardBitmap?.let {
                    PhotoCardShareBottomSheet(photoCardBitmap = it, sendAction = sendAction)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoCardShareBottomSheet(
    photoCardBitmap: ImageBitmap,
    sendAction: (PhotoCardResultIntent) -> Unit,
) {
    WepliBottomSheet(
        onClosed = { sendAction(PhotoCardResultIntent.ShowShareBottomSheet(false)) },
        type = WepliBottomSheetType.Normal(title = "포토카드 공유하기"),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(180.dp),
                painter = BitmapPainter(photoCardBitmap),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(16.dp))
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
                text = "스크린샷으로 저장하기",
                onClick = { }
            )
        }
    }
}

@Composable
fun BottomSheetItem(
    @DrawableRes iconRes: Int,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(vertical = 14.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = null,
            tint = WepliTheme.color.gray900
        )

        Text(
            text = text,
            style = WepliTheme.typo.body4,
            color = WepliTheme.color.gray900,
        )
    }
}

@Preview
@Composable
fun PhotoCardResultScreenPreview() {
    PhotoCardResultScreen(
        PhotoCardResultUiState(
            photoCardInfo = PhotoCardUiData(
                nickname = userMockData.random().nickname,
                userTendency = "Melody Memories",
                oneLineIntro = "안녕하세요! 저는 음악을 좋아하는 사람입니다.",
                instagramId = "wepli",
                favoriteSong = songMockData.random()
            )
        ),
        {},
        {}
    )
}

private suspend fun capturePhotoCard(graphicsLayer: GraphicsLayer): ImageBitmap = withContext(Dispatchers.Default) {
    graphicsLayer.toImageBitmap()
}

private fun onClickSaveBtn(context: Context, scope: CoroutineScope, graphicsLayer: GraphicsLayer) {
    scope.launch {
        val bitmap = capturePhotoCard(graphicsLayer).toAndroidBitmap()
        bitmap.saveBitmapToFile(
            context = context,
            fileName = "wepli_photocard_${System.currentTimeMillis()}",
            onSuccess = {
                Toast.makeText(context, "포토카드가 저장 되었어요. 갤러리에서 확인해보세요!", Toast.LENGTH_SHORT).show()
            },
            onFailure = {
                Toast.makeText(context, "포토카드 저장에 실패했어요", Toast.LENGTH_SHORT).show()
            }
        )
    }
}
