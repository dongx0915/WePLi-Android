package com.wepli.mypage.menus.profile.viewmodel

import base.BaseMviViewModel
import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.shared.feature.uimodel.user.UserUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import model.tendency.Tendency
import javax.inject.Inject


data class ProfileState(
    val user: UserUiData = UserUiData(),
    val tendency: Tendency = Tendency.BASIC_RHYTHM,
) : UiState

sealed interface ProfileEffect : SideEffect

sealed interface ProfileIntent : Intent

@HiltViewModel
class ProfileViewModel @Inject constructor() : BaseMviViewModel<ProfileState, ProfileEffect, ProfileIntent>(
    initialState = ProfileState()
) {
    override fun processIntent(intent: ProfileIntent) {
        TODO("Not yet implemented")
    }
}