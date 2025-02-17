package com.wepli.feature.namecard.result

import base.BaseMviViewModel
import com.wepli.feature.namecard.result.mvi.NameCardResultEffect
import com.wepli.feature.namecard.result.mvi.NameCardResultIntent
import com.wepli.feature.namecard.result.mvi.NameCardResultUiState
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
    }

    override fun processIntent(intent: NameCardResultIntent) {
        TODO("Not yet implemented")
    }


    private fun setUserInfo() = launch {
        userRepository.getUser()?.let {
            updateState { copy(user = UserUiData.fromDomain(it)) }
        }
    }
}