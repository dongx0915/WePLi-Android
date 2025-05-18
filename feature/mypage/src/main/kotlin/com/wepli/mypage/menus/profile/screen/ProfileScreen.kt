package com.wepli.mypage.menus.profile.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import appbar.AppBarIcon
import appbar.TextType
import appbar.WepliAppBar
import com.wepli.feature.mypage.R
import com.wepli.mypage.component.ProfileImage
import com.wepli.mypage.menus.profile.viewmodel.ProfileEffect
import com.wepli.mypage.menus.profile.viewmodel.ProfileIntent
import com.wepli.mypage.menus.profile.viewmodel.ProfileState
import com.wepli.mypage.menus.profile.viewmodel.ProfileViewModel
import com.wepli.shared.feature.uimodel.tendency.toIconResId
import component.bottomsheet.WepliBottomSheet
import component.bottomsheet.WepliBottomSheetType
import model.tendency.Tendency
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import textfield.FieldLabel
import textfield.LimitedLengthTextField
import textfield.WepliTextFieldType
import theme.WepliTheme
import com.wepli.core.resources.R as CoreR

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(ProfileState(), {}, {})
}

@Composable
fun ProfileScreenRoute(navOnBack: () -> Unit) {
    val viewModel = hiltViewModel<ProfileViewModel>()
    val state by viewModel.collectAsState()

    viewModel.collectSideEffect {
        when(it) {
            ProfileEffect.ProfileUpdateSuccess -> {
                navOnBack()
            }
            ProfileEffect.ProfileUpdateFailed -> {

            }
        }
    }

    ProfileScreen(state = state, navOnBack = navOnBack, sendAction = viewModel::processIntent)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileScreen(
    state: ProfileState,
    navOnBack: () -> Unit,
    sendAction: (ProfileIntent) -> Unit
) {
    Scaffold(
        containerColor = WepliTheme.color.black,
        topBar = {
            WepliAppBar(
                containerColor = Color.Transparent,
                title = stringResource(R.string.profile_title),
                showBackButton = true,
                onClickBack = navOnBack,
                actionIcons = listOf {
                    AppBarIcon(
                        icon = TextType.Gradient(
                            brush = WepliTheme.color.linear3,
                            textResource = CoreR.string.common_complete,
                            onClick = { sendAction(ProfileIntent.OnCompleteProfileEdit) }
                        ),
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
            NicknameLayout(state = state, maxLength = 20, sendAction = sendAction)

            Spacer(modifier = Modifier.height(24.dp))
            TendencyField(
                tendency = state.user.tendency,
                sendAction = sendAction
            )

            if (state.isShownTendencyBottomSheet) {
                TendencySelectBottomSheet(state, sendAction)
            }
        }
    }
}

@Composable
fun NicknameLayout(
    state: ProfileState,
    maxLength: Int,
    sendAction: (ProfileIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FieldLabel(
            text = stringResource(R.string.profile_nickname),
            label = "*",
            isRequired = true
        )

        LimitedLengthTextField(
            value = state.user.nickname,
            maxLength = maxLength,
            isLengthExceeded = state.isNicknameLengthExceeded,
            placeholder = stringResource(R.string.profile_nickname_placeholder),
            errorText = stringResource(R.string.profile_nickname_error, maxLength),
            singleLine = true,
            type = WepliTextFieldType.Normal,
            onValueChanged = { newValue, maxLength ->
                sendAction(ProfileIntent.UpdateNickname(newValue, maxLength))
            },
            textFieldModifier = Modifier.height(44.dp)
        )
    }
}

@Composable
fun TendencyField(
    tendency: Tendency,
    sendAction: (ProfileIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FieldLabel(
            text = stringResource(R.string.profile_my_tendency),
            label = "*",
            isRequired = true
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { sendAction(ProfileIntent.ShowTendencyBottomSheet(true)) }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TendencySelectBottomSheet(
    state: ProfileState,
    sendAction: (ProfileIntent) -> Unit
) {
    val userTendency = state.user.tendency

    WepliBottomSheet(
        onClosed = { sendAction(ProfileIntent.ShowTendencyBottomSheet(false)) },
        type = WepliBottomSheetType.Normal(
            title = stringResource(R.string.profile_tendency_bottom_sheet_title)
        ),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Tendency.entries.forEach {
                TendencyItem(
                    tendency = it,
                    isChecked = it == userTendency,
                    sendAction = sendAction
                )
            }
        }
    }
}

@Preview
@Composable
fun TendencySelectBottomSheetPreview() {
    Column {
        Tendency.entries.forEach {
            TendencyItem(it, false, {})
        }
    }
}

@Composable
fun TendencyItem(
    tendency: Tendency,
    isChecked: Boolean,
    sendAction: (ProfileIntent) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                sendAction(ProfileIntent.UpdateTendency(tendency))
                sendAction(ProfileIntent.ShowTendencyBottomSheet(false))
            }
            .padding(vertical = 6.dp, horizontal = 20.dp),
    ) {
        Icon(
            painter = painterResource(tendency.toIconResId()),
            tint = Color.Unspecified,
            contentDescription = null,
            modifier = Modifier.size(28.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = tendency.title,
                style = WepliTheme.typo.subTitle3,
                color = WepliTheme.color.gray700
            )

            Text(
                text = tendency.description,
                style = WepliTheme.typo.body6,
                color = WepliTheme.color.gray500
            )
        }

        if (isChecked) {
            Icon(
                painter = painterResource(CoreR.drawable.ic_checkbox),
                tint = Color.Unspecified,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}