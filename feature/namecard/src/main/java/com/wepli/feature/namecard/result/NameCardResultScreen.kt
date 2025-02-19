package com.wepli.feature.namecard.result

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.AppBarIcon
import appbar.AppBarIconType
import appbar.WepliAppBar
import button.WepliBasicButton
import button.WepliButtonStyle
import com.wepli.feature.namecard.component.NameCardComponent
import com.wepli.feature.namecard.result.mvi.NameCardResultIntent
import com.wepli.feature.namecard.result.mvi.NameCardResultUiState
import com.wepli.shared.feature.mock.songMockData
import com.wepli.shared.feature.mock.userMockData
import com.wepli.shared.feature.uimodel.namecard.NameCardUiData
import compose.convertToBitmap
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import theme.WepliTheme

@Composable
fun NameCardResultScreenRoute(
    nameCardInfo: NameCardUiData?,
    navOnBack: () -> Unit
) {
    val viewModel: NameCardResultViewModel = hiltViewModel()
    val state by viewModel.collectAsState()
    nameCardInfo?.let {
        LaunchedEffect(nameCardInfo) {
            viewModel.processIntent(NameCardResultIntent.Initialize(nameCardInfo))
        }
    }

    NameCardResultScreen(state, navOnBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@Composable
fun NameCardResultScreen(
    state: NameCardResultUiState,
    navOnBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // NameCardComponent를 View로 변환할 ComposeView
    val nameCardBitmap = convertToBitmap {
        NameCardComponent(
            nameCardInfo = state.nameCardInfo,
        )
    }

    Scaffold(
        topBar = {
            WepliAppBar(
                title = "",
                showBackButton = true,
                onClickBack = navOnBack,
                actionIcons = listOf {
                    AppBarIcon(
                        icon = AppBarIconType.Save {
                            coroutineScope.launch {
                                val bitmap = nameCardBitmap.invoke()
                                saveBitmapToFile(context, bitmap, "wepli_namecard_${System.currentTimeMillis()}",
                                    onSuccess = {
                                        Toast.makeText(context, "명함이 저장 되었어요. 갤러리에서 확인해보세요!", Toast.LENGTH_SHORT).show()
                                    },
                                    onFailure = {
                                        Toast.makeText(context, "명함 저장에 실패했어요", Toast.LENGTH_SHORT).show()
                                    }
                                )
                            }
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
                text = "명함이 완성되었어요!",
                style = WepliTheme.typo.title1,
                color = WepliTheme.color.gray900
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "${state.user.nickname}님만의 명함이 완성 되었어요!\n친구들에게 공유해볼까요?",
                style = WepliTheme.typo.body4,
                color = WepliTheme.color.gray500,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.weight(4f))

            NameCardComponent(
                nameCardInfo = state.nameCardInfo,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.weight(3f))
            WepliBasicButton(
                title = "공유하기",
                isEnabled = true,
                onClick = { },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
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
        }
    }
}

@Preview
@Composable
fun NameCardResultScreenPreview() {
    NameCardResultScreen(NameCardResultUiState(
        nameCardInfo = NameCardUiData(
            nickname = userMockData.random().nickname,
            userTendency = "Melody Memories",
            oneLineIntro = "안녕하세요! 저는 음악을 좋아하는 사람입니다.",
            instagramId = "wepli",
            favoriteSong = songMockData.random()
        )
    )) {}
}

fun saveBitmapToFile(
    context: Context,
    bitmap: Bitmap,
    fileName: String,
    onSuccess: () -> Unit,
    onFailure: () -> Unit
) {
    val filename = "$fileName.png"

    // MediaStore에 저장할 파일 정보를 설정
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
    }

    // ContentResolver를 통해 이미지 저장 Uri 생성
    val resolver = context.contentResolver
    val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    if (uri == null) {
        onFailure()
        return
    }

    // 생성된 Uri에 출력 스트림을 열어 Bitmap을 저장
    resolver.openOutputStream(uri)?.use { outputStream ->
        val success = bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        onSuccess.takeIf { success } ?: onFailure()
    } ?: {
        onFailure()
    }
}