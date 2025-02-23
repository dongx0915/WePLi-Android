package com.wepli.feature.photocard.main.viewmodel

import base.BaseMviViewModel
import com.wepli.feature.photocard.main.mvi.NameCardMainEffect
import com.wepli.feature.photocard.main.mvi.NameCardMainIntent
import com.wepli.feature.photocard.main.mvi.PhotoCardMainUiState
import com.wepli.shared.feature.mock.songMockData
import com.wepli.shared.feature.mock.userMockData
import com.wepli.shared.feature.uimodel.namecard.PhotoCardUiData
import com.wepli.shared.feature.uimodel.user.UserUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import repository.user.UserRepository
import javax.inject.Inject

@HiltViewModel
class NameCardMainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseMviViewModel<PhotoCardMainUiState, NameCardMainEffect, NameCardMainIntent>(
    initialState = PhotoCardMainUiState(),
) {
    init {
        getUser()
        setNameCardInfo()
    }

    override fun processIntent(intent: NameCardMainIntent) {

    }

    private fun getUser() = launch {
        userRepository.getUser()?.let {
            updateState { copy(user = UserUiData.fromDomain(it)) }
        }
    }

    private fun setNameCardInfo() {
        val user = userMockData.random()
        val nameCardInfo = PhotoCardUiData(
            nickname = user.nickname,
            userTendency = "Melody Memories",
            profileImg = user.profileImgUrl,
            oneLineIntro = "내 취향을 다른 사람들에게 소개해보세요.",
            instagramId = "my_account",
            favoriteSong = songMockData.random()
        )

        updateState { copy(photoCardInfo = nameCardInfo) }
    }
}