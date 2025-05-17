package com.wepli.mypage.menus.profile.viewmodel

import android.util.Log
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
    val isShownTendencyBottomSheet: Boolean = false,
) : UiState

sealed interface ProfileEffect : SideEffect {
    data object ProfileUpdateSuccess : ProfileEffect
    data object ProfileUpdateFailed : ProfileEffect
}

sealed interface ProfileIntent : Intent {
    data class ShowTendencyBottomSheet(val isShown: Boolean) : ProfileIntent
    data class UpdateTendency(val tendency: Tendency) : ProfileIntent
    data object OnCompleteProfileEdit : ProfileIntent
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
            is ProfileIntent.UpdateTendency -> updateState {
                copy(user = user.copy(tendency = intent.tendency))
            }
            ProfileIntent.OnCompleteProfileEdit -> postSideEffect { ProfileEffect.ProfileUpdateSuccess }
        }
    }

    private fun loadUserData() = launch {
        userRepository.getUser()?.let {
            Log.d("USER", it.toString())
            updateState { copy(user = UserUiData.fromDomain(it)) }
        }
    }
}