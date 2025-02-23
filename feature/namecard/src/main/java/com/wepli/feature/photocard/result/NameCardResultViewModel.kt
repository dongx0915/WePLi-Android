package com.wepli.feature.photocard.result

import base.BaseMviViewModel
import com.wepli.feature.photocard.result.mvi.PhotoCardResultEffect
import com.wepli.feature.photocard.result.mvi.PhotoCardResultIntent
import com.wepli.feature.photocard.result.mvi.PhotoCardResultUiState
import com.wepli.shared.feature.uimodel.user.UserUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import repository.user.UserRepository
import javax.inject.Inject

@HiltViewModel
class NameCardResultViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseMviViewModel<PhotoCardResultUiState, PhotoCardResultEffect, PhotoCardResultIntent>(
    initialState = PhotoCardResultUiState()
) {

    init {
        setUserInfo()
    }

    override fun processIntent(intent: PhotoCardResultIntent) {
        when(intent) {
            is PhotoCardResultIntent.Initialize -> {
                updateState { copy(photoCardInfo = intent.photoCardInfo) }
            }
            is PhotoCardResultIntent.ShowShareBottomSheet -> {
                updateState { copy(isShownShareBottomSheet = intent.isShown) }
            }
        }
    }

    private fun setUserInfo() = launch {
        userRepository.getUser()?.let {
            updateState { copy(user = UserUiData.fromDomain(it)) }
        }
    }
}