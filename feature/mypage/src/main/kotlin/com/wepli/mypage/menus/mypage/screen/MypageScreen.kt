package com.wepli.mypage.menus.mypage.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.WepliAppBar
import com.wepli.designsystem.R
import com.wepli.mypage.component.MenuComponent
import com.wepli.mypage.component.MenuTitleComponent
import com.wepli.mypage.menus.mypage.viewmodel.MyPageUiState
import com.wepli.mypage.menus.mypage.viewmodel.MyPageViewModel
import com.wepli.shared.feature.mock.userMockData
import com.wepli.shared.feature.uimodel.user.UserUiData
import image.AsyncImageWithPreview
import org.orbitmvi.orbit.compose.collectAsState
import theme.WepliTheme

@Preview
@Composable
fun MyPageScreenPreview() {
    MyPageScreen(
        user = userMockData.random(),
        navOnAppInfo = {}
    )
}

@Composable
fun MyPageScreenRoute(
    viewModel: MyPageViewModel = hiltViewModel(),
    navOnAppInfo: () -> Unit,
) {
    val state: MyPageUiState by viewModel.collectAsState()
    val user: UserUiData = state.user

    MyPageScreen(
        user = user,
        navOnAppInfo = navOnAppInfo
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageScreen(
    user: UserUiData,
    navOnAppInfo: () -> Unit,
) {
    val scrollState = rememberScrollState()

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
                .verticalScroll(scrollState)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            ProfileLayout(
                modifier = Modifier.padding(horizontal = 20.dp),
                nickname = user.nickname,
                email = user.email,
                profileImgUrl = user.profileImgUrl,
            )

            TendencyComponent(modifier = Modifier.padding(horizontal = 20.dp))

            MenuLayout(navOnAppInfo = navOnAppInfo, onAction = onAction)

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
fun ProfileImage(
    modifier: Modifier = Modifier,
    profileImgUrl: String,
) {
    val imageModifier = modifier
        .border(
            width = 1.dp,
            brush = WepliTheme.color.linear3,
            shape = CircleShape
        )
        .clip(CircleShape)

    Box {
        AsyncImageWithPreview(
            modifier = imageModifier,
            imageUrl = profileImgUrl,
            previewImage = painterResource(R.drawable.img_placeholder_eunbin)
        )

        Image(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(20.dp),
            painter = painterResource(id = R.drawable.ic_profile_camera),
            contentDescription = null
        )
    }
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
            modifier = Modifier.size(60.dp),
            profileImgUrl = profileImgUrl,
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

@Preview
@Composable
fun TendencyComponent(
    modifier: Modifier = Modifier,
    tendencyTitle: String = "센티멘탈 심포니",
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
            painter = painterResource(id = R.drawable.img_crystal_ball),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = tendencyTitle,
            style = WepliTheme.typo.subTitle5,
            color = WepliTheme.color.gray700
        )
    }
}

@Composable
fun MenuLayout(
    navOnAppInfo: () -> Unit = {},
    onAction: (MyPageIntent) -> Unit,
) {
    Column {
        MyActivityMenuLayout()
        SettingMenuLayout()
        AppInfoMenuLayout(
            navOnAppInfo = { navOnAppInfo() }
        )
        EtcMenuLayout(onAction = onAction)
    }
}


@Composable
fun MyActivityMenuLayout() {
    Column {
        MenuTitleComponent(title = "내 활동")
        MenuComponent(title = "내 플레이리스트")
        MenuComponent(title = "참여한 릴레이리스트")
        MenuComponent(title = "좋아요 • 저장")
    }
}

@Composable
fun SettingMenuLayout() {
    Column {
        MenuTitleComponent(title = "설정")
        MenuComponent(title = "알림 설정")
    }
}

@Composable
fun AppInfoMenuLayout(
    navOnAppInfo: () -> Unit
) {
    Column {
        MenuTitleComponent(title = "앱 정보")
        MenuComponent(title = "서비스 이용 가이드")
        MenuComponent(title = "공지 • 이용약관") {
            navOnAppInfo()
        }
        MenuComponent(title = "앱 버전")
    }
}

@Composable
fun EtcMenuLayout(onAction: (MyPageIntent) -> Unit) {
    Column {
        MenuTitleComponent(title = "기타")
        MenuComponent(title = "로그아웃") {
            onAction(MyPageIntent.ShowLogoutPopup(true))
        }
    }
}

@Composable
fun FooterLayout() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 32.dp)) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Copyright ©2024 WePLi",
            style = WepliTheme.typo.body4,
            color = WepliTheme.color.gray400,
        )
    }
}