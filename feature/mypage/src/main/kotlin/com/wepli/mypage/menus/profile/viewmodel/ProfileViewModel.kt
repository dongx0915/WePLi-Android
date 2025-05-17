package com.wepli.mypage.menus.profile.viewmodel

import base.BaseMviViewModel
import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.shared.feature.uimodel.user.UserUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import model.tendency.Tendency
import repository.user.UserRepository
import javax.inject.Inject


data class ProfileState(
    val user: UserUiData = UserUiData(),
    val tendency: Tendency = Tendency.BASIC_RHYTHM,
    val isShownTendencyBottomSheet: Boolean = false,
) : UiState

sealed interface ProfileEffect : SideEffect

sealed interface ProfileIntent : Intent {
    data class ShowTendencyBottomSheet(val isShown: Boolean) : ProfileIntent
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseMviViewModel<ProfileState, ProfileEffect, ProfileIntent>(
    initialState = ProfileState()
) {
    init {
        loadUserData()
    }

    override fun processIntent(intent: ProfileIntent) {
        when(intent) {
            is ProfileIntent.ShowTendencyBottomSheet -> updateState { copy(isShownTendencyBottomSheet = intent.isShown) }
        }
    }

    private fun loadUserData() = launch {
        userRepository.getUser()?.let {
            updateState { copy(user = UserUiData.fromDomain(it)) }
        }
    }
}