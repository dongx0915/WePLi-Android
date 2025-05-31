package com.wepli.mypage.menus.mypage.screen

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.WepliAppBar
import com.wepli.core.resources.R as CoreR
import com.wepli.mypage.common.MenuSection
import com.wepli.mypage.component.MenuLayout
import com.wepli.mypage.component.ProfileImage
import com.wepli.mypage.menus.mypage.viewmodel.MyPageEffect
import com.wepli.mypage.menus.mypage.viewmodel.MyPageIntent
import com.wepli.mypage.menus.mypage.viewmodel.MyPageUiState
import com.wepli.mypage.menus.mypage.viewmodel.MyPageViewModel
import com.wepli.shared.feature.mock.userMockData
import com.wepli.shared.feature.uimodel.tendency.toIconResId
import com.wepli.shared.feature.uimodel.user.UserUiData
import component.dialog.WepliDialog
import component.dialog.WepliDialogType
import dev.chrisbanes.haze.hazeSource
import model.tendency.Tendency
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import theme.LocalHazeState
import theme.WepliTheme

@Preview(heightDp = 2000)
@Composable
fun MyPageScreenPreview() {
    MyPageScreen(
        user = userMockData.random(),
        menuSections = listOf(
            MenuSection(
                title = "내 활동",
                items = listOf(
                    MenuSection.MenuItem("내 플레이리스트", MyPageIntent.None),
                    MenuSection.MenuItem("참여한 릴레이리스트", MyPageIntent.None),
                    MenuSection.MenuItem("좋아요 • 저장", MyPageIntent.None),
                )
            ),
            MenuSection(
                title = "설정",
                items = listOf(
                    MenuSection.MenuItem("알림 설정", MyPageIntent.None),
                )
            ),
            MenuSection(
                title = "앱 정보",
                items = listOf(
                    MenuSection.MenuItem("서비스 이용 가이드", MyPageIntent.None),
                    MenuSection.MenuItem("공지 • 이용약관", MyPageIntent.None),
                    MenuSection.MenuItem("앱 버전", MyPageIntent.None),
                )
            ),
            MenuSection(
                title = "기타",
                items = listOf(
                    MenuSection.MenuItem("로그아웃", MyPageIntent.None),
                )
            )
        ),
        showLogoutPopup = false,
        navOnAppInfo = {},
        navOnProfile = {},
        onAction = {}
    )
}

private fun handleSideEffect(
    context: Context,
    sideEffect: MyPageEffect,
    navOnAppInfo: () -> Unit,
    navOnPhotoCard: () -> Unit,
    navOnDevMode: () -> Unit,
    goToLoginActivity: () -> Unit
) {
    when (sideEffect) {
        is MyPageEffect.SuccessLogout -> {
            Toast.makeText(context, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
            goToLoginActivity()
        }
        is MyPageEffect.FailedLogout -> {
            Toast.makeText(context, "로그아웃에 실패했습니다.", Toast.LENGTH_SHORT).show()
        }
        MyPageEffect.NavigateOnAppInfo -> navOnAppInfo()
        MyPageEffect.NavigateOnPhotoCard -> navOnPhotoCard()
        MyPageEffect.NavigateOnDevMode -> navOnDevMode()
    }
}

@Composable
fun MyPageScreenRoute(
    viewModel: MyPageViewModel = hiltViewModel(),
    navOnAppInfo: () -> Unit,
    navOnPhotoCard: () -> Unit,
    navOnProfile: () -> Unit,
    navOnDevMode: () -> Unit,
    goToLoginActivity: () -> Unit,
) {
    val context: Context = LocalContext.current
    val state: MyPageUiState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect -> handleSideEffect(context, sideEffect, navOnAppInfo, navOnPhotoCard, navOnDevMode, goToLoginActivity) }

    MyPageScreen(
        user = state.user,
        menuSections = state.menuSections,
        showLogoutPopup = state.showLogoutPopup,
        navOnAppInfo = navOnAppInfo,
        navOnProfile = navOnProfile,
        onAction = viewModel::processIntent
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageScreen(
    user: UserUiData,
    menuSections: List<MenuSection>,
    showLogoutPopup: Boolean,
    navOnAppInfo: () -> Unit,
    navOnProfile: () -> Unit,
    onAction: (MyPageIntent) -> Unit,
) {
    val scrollState = rememberScrollState()
    val blurState = LocalHazeState.current

    Scaffold(
        containerColor = WepliTheme.color.black,
        topBar = {
            WepliAppBar(
                containerColor = Color.Transparent,
                title = "마이페이지"
            )
        }
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .padding(bottom = paddingValues.calculateBottomPadding())
                .hazeSource(blurState)
                .verticalScroll(scrollState)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ProfileLayout(
                modifier = Modifier.padding(horizontal = 20.dp).clickable { navOnProfile() },
                nickname = user.nickname,
                email = user.email,
                profileImgUrl = user.profileImgUrl,
            )

            TendencyComponent(
                tendency = user.tendency,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            MenuLayout(
                sections = menuSections,
                onAction = { (it as? MyPageIntent)?.let(onAction::invoke) }
            )

            FooterLayout()
        }
    }

    if (showLogoutPopup) {
        LogoutDialog(onAction)
    }
}

@Composable
fun LogoutDialog(
    onAction: (MyPageIntent) -> Unit
) {
    WepliDialog(
        title = "로그아웃",
        subTitle = "로그아웃 하시겠습니까?",
        dialogType = WepliDialogType.TwoButton(
            okButtonText = "확인",
            cancelButtonText = "취소",
            okButtonClick = { onAction(MyPageIntent.RequestLogout) },
            cancelButtonClick = { onAction(MyPageIntent.ShowLogoutPopup(false)) }
        ),
    )
}

@Composable
fun ProfileLayout(
    modifier: Modifier = Modifier,
    nickname: String,
    email: String,
    profileImgUrl: String,
) {
    Row(modifier = modifier) {
        ProfileImage(
            imageSize = 60.dp,
            profileImgUrl = profileImgUrl,
            modifier = Modifier.size(60.dp),
        )
        Spacer(modifier = Modifier.width(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = nickname,
                style = WepliTheme.typo.subTitle1,
                color = WepliTheme.color.gray900
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = email,
                style = WepliTheme.typo.body4,
                color = WepliTheme.color.gray700
            )
        }
    }
}

@Composable
fun TendencyComponent(
    tendency: Tendency,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(WepliTheme.color.gray000)
            .padding(vertical = 16.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = tendency.toIconResId()),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = tendency.title,
            style = WepliTheme.typo.subTitle5,
            color = WepliTheme.color.gray700
        )
    }
}

@Composable
fun FooterLayout() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 56.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Copyright ©2024 WePLi",
            style = WepliTheme.typo.body4,
            color = WepliTheme.color.gray400,
        )
    }
}