package com.wepli.mypage.menus.profile.viewmodel

import base.BaseMviViewModel
import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.core.kotlin.flow.collectResult
import com.wepli.shared.feature.uimodel.user.UserUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import model.tendency.Tendency
import repository.user.UserRepository
import javax.inject.Inject


data class ProfileState(
    val user: UserUiData = UserUiData(),
    val isNicknameLengthExceeded: Boolean = false,
    val isShownTendencyBottomSheet: Boolean = false,
) : UiState

sealed interface ProfileEffect : SideEffect {
    data object ProfileUpdateSuccess : ProfileEffect
    data object ProfileUpdateFailed : ProfileEffect
}

sealed interface ProfileIntent : Intent {
    data class ShowTendencyBottomSheet(val isShown: Boolean) : ProfileIntent
    data class UpdateNickname(val nickname: String, val maxLength: Int) : ProfileIntent
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
            is ProfileIntent.ShowTendencyBottomSheet -> updateState {
                copy(isShownTendencyBottomSheet = intent.isShown)
            }
            is ProfileIntent.UpdateNickname -> updateNickname(intent.nickname, intent.maxLength)
            is ProfileIntent.UpdateTendency -> updateTendency(intent.tendency)
            ProfileIntent.OnCompleteProfileEdit -> updateUser()
        }
    }

    private fun loadUserData() = launch {
        userRepository.getUserLocalData()?.let {
            updateState { copy(user = UserUiData.fromDomain(it)) }
        }
    }

    private fun updateUser() = intent {
        val newUserData = UserUiData.toDomain(state.user)
        userRepository.updateUserData(newUserData)
            .collectResult(
                onSuccess = {
                    postSideEffect { ProfileEffect.ProfileUpdateSuccess }
                },
                onFailure = {
                    postSideEffect { ProfileEffect.ProfileUpdateFailed }
                }
            )
    }

    private fun updateNickname(newNickname: String, maxLength: Int) {
        updateState {
            copy(
                user = user.copy(nickname = newNickname),
                isNicknameLengthExceeded = newNickname.length > maxLength
            )
        }
    }

    private fun updateTendency(newTendency: Tendency) {
        updateState {
            copy(user = user.copy(tendency = newTendency))
        }
    }
}