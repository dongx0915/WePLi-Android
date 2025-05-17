package com.wepli.mypage.menus.profile.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.AppBarIcon
import appbar.TextType
import appbar.WepliAppBar
import com.wepli.feature.mypage.R
import com.wepli.mypage.component.ProfileImage
import com.wepli.mypage.menus.profile.viewmodel.ProfileIntent
import com.wepli.mypage.menus.profile.viewmodel.ProfileState
import com.wepli.mypage.menus.profile.viewmodel.ProfileViewModel
import com.wepli.shared.feature.uimodel.tendency.toIconResId
import model.tendency.Tendency
import org.orbitmvi.orbit.compose.collectAsState
import textfield.FieldLabel
import textfield.LimitedLengthTextField
import textfield.WepliTextFieldType
import theme.WepliTheme
import com.wepli.core.resources.R as CoreR

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(ProfileState())
}

@Composable
fun ProfileScreenRoute() {
    val viewModel = hiltViewModel<ProfileViewModel>()
    val state by viewModel.collectAsState()

    ProfileScreen(state = state)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileScreen(
    state: ProfileState
) {
    Scaffold(
        containerColor = WepliTheme.color.black,
        topBar = {
            WepliAppBar(
                containerColor = Color.Transparent,
                title = "내 정보 수정",
                showBackButton = true,
                actionIcons = listOf {
                    AppBarIcon(
                        icon = TextType.Gradient(
                            brush = WepliTheme.color.linear3,
                            textResource = CoreR.string.common_complete
                        )
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            ProfileImage(
                imageSize = 84.dp,
                profileImgUrl = state.user.profileImgUrl,
                modifier = Modifier.size(84.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))
            NicknameLayout(
                nickname = state.user.nickname,
                maxLength = 16,
                isTitleLengthExceeded = false,
                sendAction = {},
            )

            Spacer(modifier = Modifier.height(24.dp))
            TendencyLayout(
                tendency = state.tendency,
                maxLength = 20,
                isTitleLengthExceeded = false,
                sendAction = {}
            )
        }
    }
}

@Composable
fun NicknameLayout(
    nickname: String,
    maxLength: Int,
    isTitleLengthExceeded: Boolean,
    sendAction: (ProfileIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FieldLabel("닉네임", "*", true)
        LimitedLengthTextField(
            value = nickname,
            maxLength = maxLength,
            isLengthExceeded = isTitleLengthExceeded,
            placeholder = "변경할 닉네임을 입력해주세요.",
            errorText = "닉네임은 ${maxLength}자 이내로 작성해주세요.",
            singleLine = true,
            type = WepliTextFieldType.Normal,
            onValueChanged = { newValue, maxLength ->

            },
            modifier = Modifier.height(44.dp)
        )
    }
}

@Composable
fun TendencyLayout(
    tendency: Tendency,
    maxLength: Int,
    isTitleLengthExceeded: Boolean,
    sendAction: (ProfileIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FieldLabel("나의 성향", "*", true)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(color = WepliTheme.color.gray000)
                .padding(horizontal = 16.dp)
                .height(44.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(tendency.toIconResId()),
                    contentDescription = null
                )

                Text(
                    text = tendency.title,
                    style = WepliTheme.typo.subTitle5,
                    color = WepliTheme.color.gray700,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = ImageVector.vectorResource(CoreR.drawable.ic_arrow_down_vector),
                    tint = Color.Unspecified,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}