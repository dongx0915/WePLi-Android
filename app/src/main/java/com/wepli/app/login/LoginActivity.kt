package com.wepli.app.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wepli.app.MainActivity
import com.wepli.app.R
import common.WepliSpacer
import dagger.hilt.android.AndroidEntryPoint
import extensions.compose.gesturesDisabled
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import theme.WePLiTheme

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WePLiTheme {
                LoginScreen {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateMain: () -> Unit = {}
) {
    val context = LocalContext.current
    val credentialManager = CredentialManager.create(context)

    // Effect 처리
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is LoginEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                LoginEffect.NavigateToMain -> {
                    onNavigateMain()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WePLiTheme.color.black)
            .padding(top = 100.dp, bottom = 48.dp),
    ) {
        LoginTitleComponent()

        WepliSpacer(vertical = 40.dp)
        GenreLayout()

        WepliSpacer(vertical = 16.dp)
        GenreLayout(
            contentPadding = PaddingValues(start = 28.dp)
        )

        Spacer(modifier = Modifier.weight(1f))
        SocialLoginButton(
            modifier = Modifier.padding(horizontal = 24.dp),
            iconVector = ImageVector.vectorResource(id = R.drawable.ic_google_logo),
            buttonText = "Google로 시작하기",
        ) {
            LoginIntent.RequestGoogleLogin { request ->
                credentialManager.getCredential(request = request, context = context)
            }.let(viewModel::processIntent)
        }

        TermsText()
    }
}

@Composable
fun LoginTitleComponent() {
    Text(
        modifier = Modifier.padding(horizontal = 24.dp),
        text = "함께 만드는 플레이리스트",
        style = WePLiTheme.typo.title2,
        color = WePLiTheme.color.white,
    )
    WepliSpacer(vertical = 16.dp)
    Text(
        modifier = Modifier.padding(horizontal = 24.dp),
        text = "다양한 사람들과 음악으로 연결되는\n특별한 순간을 경험해보세요.",
        style = WePLiTheme.typo.body4,
        color = WePLiTheme.color.white.copy(alpha = 0.6f),
    )
}

@Composable
fun GenreLayout(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val listState = rememberLazyListState()

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = contentPadding,
        modifier = modifier
            .fillMaxWidth()
            .gesturesDisabled(disabled = true),
    ) {
        items(Int.MAX_VALUE) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(26.dp),
                    painter = painterResource(id = com.wepli.designsystem.R.drawable.img_placeholder_chuu),
                    contentDescription = null
                )
                WepliSpacer(horizontal = 6.dp)
                Text(
                    text = "#여행 플리",
                    style = WePLiTheme.typo.body5,
                    color = WePLiTheme.color.white.copy(alpha = 0.7f),
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
            .background(WePLiTheme.color.gray000)
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
            style = WePLiTheme.typo.body1,
            color = WePLiTheme.color.white,
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
            style = WePLiTheme.typo.caption1,
            color = WePLiTheme.color.gray500,
        )
    }
}

@Preview
@Composable
fun GoogleLoginButtonPreview() {
    SocialLoginButton(
        iconVector = ImageVector.vectorResource(id = R.drawable.ic_google_logo),
        buttonText = "Google로 시작하기",
        onClick = {}
    )
}