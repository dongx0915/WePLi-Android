package com.wepli.feature.namecard.result

import base.BaseMviViewModel
import com.wepli.feature.namecard.result.mvi.NameCardResultEffect
import com.wepli.feature.namecard.result.mvi.NameCardResultIntent
import com.wepli.feature.namecard.result.mvi.NameCardResultUiState
import com.wepli.shared.feature.mock.songMockData
import com.wepli.shared.feature.uimodel.namecard.NameCardUiData
import com.wepli.shared.feature.uimodel.user.UserUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import repository.user.UserRepository
import javax.inject.Inject

@HiltViewModel
class NameCardResultViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseMviViewModel<NameCardResultUiState, NameCardResultEffect, NameCardResultIntent>(
    initialState = NameCardResultUiState()
) {

    init {
        setUserInfo()
        setNameCardInfo()
    }

    override fun processIntent(intent: NameCardResultIntent) {
        TODO("Not yet implemented")
    }


    private fun setUserInfo() = launch {
        userRepository.getUser()?.let {
            updateState { copy(user = UserUiData.fromDomain(it)) }
        }
    }

    private fun setNameCardInfo() {
        val nameCardInfo = NameCardUiData(
            nickname = "테스트 닉네임",
            userTendency = "Melody Memories",
            oneLineIntro = "테스트 문구입니다. 자신의 취향을 소개하는 문구를 작성해보세요.",
            instagramId = "dongx._.2",
            favoriteSong = songMockData.random()
        )

        updateState { copy(nameCardInfo = nameCardInfo) }
    }
}