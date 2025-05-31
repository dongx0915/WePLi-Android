package com.wepli.mypage.menus.profile.viewmodel

import base.BaseMviViewModel
import base.Intent
import base.LoadingState
import base.SideEffect
import base.UiState
import com.wepli.core.kotlin.flow.collectResult
import com.wepli.shared.feature.uimodel.user.UserUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import extensions.compressImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import model.tendency.Tendency
import model.user.usecase.UploadProfileImageUseCase
import repository.user.UserRepository
import javax.inject.Inject


data class ProfileState(
    val user: UserUiData = UserUiData(),
    val isNicknameLengthExceeded: Boolean = false,
    val isShownTendencyBottomSheet: Boolean = false,
    override val isLoading: Boolean = false,
) : UiState, LoadingState {

    fun isValidNickname(): Boolean {
        return user.nickname.isNotBlank() && !isNicknameLengthExceeded
    }
}

sealed interface ProfileEffect : SideEffect {
    data object ProfileUpdateSuccess : ProfileEffect
    data object ProfileUpdateFailed : ProfileEffect
    data object ImageSelectFailed : ProfileEffect
    data object NicknameLengthExceeded : ProfileEffect
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
    private val uploadProfileImageUseCase: UploadProfileImageUseCase,
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

    private fun updateUser() = intent {
        if (state.isValidNickname().not()) {
            postSideEffect(ProfileEffect.NicknameLengthExceeded)
            return@intent
        }

        launch(
            onStart = { copy(isLoading = true) },
            onComplete = { copy(isLoading = false) }
        ) {
            val newUserData = UserUiData.toDomain(state.user)
            val compressedImageData = pendingImageData?.compressImage()

            uploadProfileImageUseCase
                .invoke(newUserData, compressedImageData, pendingFileExtension)
                .flowOn(Dispatchers.IO)
                .collectResult(
                    onSuccess = {
                        postSideEffect { ProfileEffect.ProfileUpdateSuccess }
                    },
                    onFailure = {
                        postSideEffect { ProfileEffect.ProfileUpdateFailed }
                    }
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