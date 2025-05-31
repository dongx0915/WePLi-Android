package com.wepli.mypage.menus.profile.viewmodel

import base.BaseMviViewModel
import base.Intent
import base.LoadingState
import base.SideEffect
import base.UiState
import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.core.kotlin.flow.collectResult
import com.wepli.shared.feature.uimodel.user.UserUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import model.supabase.repository.SupabaseBucketRepository
import model.tendency.Tendency
import repository.user.UserRepository
import javax.inject.Inject


data class ProfileState(
    val user: UserUiData = UserUiData(),
    val isNicknameLengthExceeded: Boolean = false,
    val isShownTendencyBottomSheet: Boolean = false,
    override val isLoading: Boolean = false,
) : UiState, LoadingState

sealed interface ProfileEffect : SideEffect {
    data object ProfileUpdateSuccess : ProfileEffect
    data object ProfileUpdateFailed : ProfileEffect
    data object ImageSelectFailed : ProfileEffect
}

sealed interface ProfileIntent : Intent {
    data class ShowTendencyBottomSheet(val isShown: Boolean) : ProfileIntent
    data class UpdateProfileImage(val imageUri: String, val imageByteArray: ByteArray?, val fileExtension: String?) : ProfileIntent
    data class UpdateNickname(val nickname: String, val maxLength: Int) : ProfileIntent
    data class UpdateTendency(val tendency: Tendency) : ProfileIntent
    data object OnCompleteProfileEdit : ProfileIntent
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val supabaseBucketRepository: SupabaseBucketRepository,
) : BaseMviViewModel<ProfileState, ProfileEffect, ProfileIntent>(
    initialState = ProfileState()
) {
    init {
        loadUserData()
    }

    private var pendingImageData: ByteArray? = null
    private var pendingFileExtension: String? = null

    override fun processIntent(intent: ProfileIntent) {
        when(intent) {
            is ProfileIntent.ShowTendencyBottomSheet -> updateState {
                copy(isShownTendencyBottomSheet = intent.isShown)
            }
            is ProfileIntent.UpdateProfileImage -> {
                updateProfileImage(intent.imageUri, intent.imageByteArray, intent.fileExtension)
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

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun updateUser() = intent {
        launch(
            onStart = { copy(isLoading = true) },
            onComplete = { copy(isLoading = false) }
        ) {
            val newUserData = UserUiData.toDomain(state.user)
            val updateFlow: FlowResult<Unit> = if (pendingImageData != null && pendingFileExtension != null) {
                supabaseBucketRepository.uploadFile("profile", pendingImageData!!, pendingFileExtension!!)
                    .flatMapConcat { result ->
                        result.fold(
                            onSuccess = {
                                val updatedUser = newUserData.copy(profileImgUrl = it.path)

                                userRepository.updateUserData(updatedUser)
                            },
                            onFailure = { flowOf(Result.failure(it)) }
                        )
                    }
            } else {
                userRepository.updateUserData(newUserData)
            }

            updateFlow.collectResult(
                onSuccess = { postSideEffect { ProfileEffect.ProfileUpdateSuccess } },
                onFailure = { postSideEffect { ProfileEffect.ProfileUpdateFailed } }
            )
        }
    }

    private fun updateProfileImage(imageUri: String, imageByteArray: ByteArray?, fileExtension: String?) {
        if (imageByteArray == null || fileExtension == null) {
            postSideEffect { ProfileEffect.ImageSelectFailed }
            return
        }

        pendingImageData = imageByteArray
        pendingFileExtension = fileExtension

        updateState {
            copy(user = user.copy(profileImgUrl = imageUri))
        }
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