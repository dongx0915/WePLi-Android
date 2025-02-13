package com.wepli.app.login

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.hilt.navigation.compose.hiltViewModel
import com.wepli.app.MainActivity
import com.wepli.designsystem.R
import com.wepli.shared.feature.mock.musicMockData
import common.WepliSpacer
import component.dialog.WepliDialog
import component.dialog.WepliDialogType
import dagger.hilt.android.AndroidEntryPoint
import extensions.compose.gesturesDisabled
import extensions.compose.shimmerEffect
import extensions.compose.toPx
import image.AsyncImageWithPreview
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import theme.WePLiTheme
import theme.WepliTheme

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WePLiTheme {
                val viewModel = hiltViewModel<LoginViewModel>()
                val state: LoginState by viewModel.collectAsState()

                viewModel.collectSideEffect {
                    when (it) {
                        is LoginEffect.GoogleLoginError -> {
                            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        }
                        is LoginEffect.PromptAddGoogleAccount -> {
                            val intent = Intent(Settings.ACTION_ADD_ACCOUNT).apply {
                                putExtra(Settings.EXTRA_ACCOUNT_TYPES, arrayOf("com.google"))
                            }
                            startActivity(intent)
                        }
                        LoginEffect.GoogleSessionError -> {
                            Toast.makeText(this, "유저 정보 조회에 실패하였습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                        }
                        LoginEffect.NavigateToMain -> {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }
                }

                LoginScreen(
                    albumImages = state.albumImages,
                    isAddAccountDialogVisible = state.isAddAccountDialogVisible,
                    onSendIntent = viewModel::processIntent
                )
            }
        }
    }
}

@Composable
fun LoginScreen(
    albumImages: List<String>,
    isAddAccountDialogVisible: Boolean,
    onSendIntent: (LoginIntent) -> Unit
) {
    val context = LocalContext.current
    val credentialManager = CredentialManager.create(context)
    val navBarBottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WepliTheme.color.black)
            .padding(top = 130.dp, bottom = navBarBottomPadding + 48.dp)
    ) {
        LoginTitleComponent()
        WepliSpacer(vertical = 48.dp)
        PlaylistCoverPager(imageList = albumImages)

        Spacer(modifier = Modifier.weight(1f))
        SocialLoginButton(
            modifier = Modifier.padding(horizontal = 24.dp),
            iconVector = ImageVector.vectorResource(id = com.wepli.app.R.drawable.ic_google_logo),
            buttonText = "Google로 시작하기",
        ) {
            onSendIntent(
                LoginIntent.RequestGoogleLogin { request ->
                    credentialManager.getCredential(request = request, context = context)
                }
            )
        }

        TermsText()

        if (isAddAccountDialogVisible) {
            AddGoogleAccountDialog(sendAction = onSendIntent)
        }
    }
}

@Composable
fun LoginTitleComponent() {
    Text(
        modifier = Modifier.padding(horizontal = 24.dp),
        text = "함께 만드는\n플레이리스트",
        style = WepliTheme.typo.title1,
        color = WepliTheme.color.white,
    )
    WepliSpacer(vertical = 16.dp)
    Text(
        modifier = Modifier.padding(horizontal = 24.dp),
        text = "다양한 사람들과 음악으로 연결되는\n특별한 순간을 경험해보세요.",
        style = WepliTheme.typo.body3,
        color = WepliTheme.color.white.copy(alpha = 0.6f),
    )
}

@Composable
fun PlaylistCoverPager(
    modifier: Modifier = Modifier,
    imageList: List<String>,
) {
    if (imageList.isEmpty()) return
    val listState = rememberLazyListState()
    val layoutInfo = remember { derivedStateOf { listState.layoutInfo } }.value

    var targetIndex by remember { mutableIntStateOf(0) }
    val itemOffset = 48.dp.toPx()

    LaunchedEffect(Unit) { listState.scrollToItem(targetIndex, itemOffset) }
    LaunchedEffect(targetIndex) {
        delay(2000)
        val nextIndex = (targetIndex + 1) % imageList.size
        listState.animateScrollToItem(nextIndex, itemOffset)
        targetIndex = nextIndex
    }

    LazyRow(
        state = listState,
        modifier = modifier
            .fillMaxWidth()
            .gesturesDisabled(disabled = true),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(Int.MAX_VALUE) { index ->
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            val secondVisibleIndex = visibleItemsInfo.getOrNull(1)?.index // 두 번째로 보이는 아이템의 인덱스

            val isTargetItem = index == secondVisibleIndex
            val animatedSize = animateDpAsState(
                targetValue = if (isTargetItem) 100.dp else 60.dp,
                animationSpec = tween(durationMillis = 250)
            )

            val animatedCornerRadius = animateDpAsState(
                targetValue = if (isTargetItem) 8.dp else 4.dp,
                animationSpec = tween(durationMillis = 250)
            )

            Box(
                modifier = Modifier.height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImageWithPreview(
                    modifier = Modifier
                        .size(animatedSize.value)
                        .clip(RoundedCornerShape(animatedCornerRadius.value)),
                    imageUrl = imageList[index % imageList.size],
                    previewImage = painterResource(id = R.drawable.img_placeholder_eunbin),
                    loadingContent = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = WepliTheme.color.gray500)
                                .shimmerEffect(animatedCornerRadius.value)
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun SocialLoginButton(
    modifier: Modifier = Modifier,
    iconVector: ImageVector,
    buttonText: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(WepliTheme.color.gray000)
            .height(52.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp),
            imageVector = iconVector,
            tint = Color.Unspecified,
            contentDescription = null,
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = buttonText,
            style = WepliTheme.typo.body1,
            color = WepliTheme.color.white,
        )
    }
}

@Composable
fun TermsText(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        WepliSpacer(vertical = 20.dp)
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "최초 로그인은 계정을 생성하며,\n그와 동시에 서비스 조건과 개인정보처리방침에 동의하게 됩니다.",
            style = WepliTheme.typo.caption1,
            color = WepliTheme.color.gray500,
        )
    }
}

@Composable
fun AddGoogleAccountDialog(
    sendAction: (LoginIntent) -> Unit
) {
    WepliDialog(
        title = "계정 등록 안내",
        subTitle = "기기에 등록된 Google 계정이 없어요. 지금 계정을 추가하시겠어요?",
        dialogType = WepliDialogType.TwoButton(
            okButtonText = "추가",
            cancelButtonText = "취소",
            okButtonClick = { sendAction(LoginIntent.RequestAddAccountPage) },
            cancelButtonClick = { sendAction(LoginIntent.DismissAddAccountDialog) }
        ),
    )
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        albumImages = musicMockData.map { it.albumCoverUrl },
        isAddAccountDialogVisible = false,
        onSendIntent = {}
    )
}

@Preview
@Composable
fun GoogleLoginButtonPreview() {
    SocialLoginButton(
        iconVector = ImageVector.vectorResource(id = com.wepli.app.R.drawable.ic_google_logo),
        buttonText = "Google로 시작하기",
        onClick = {}
    )
}