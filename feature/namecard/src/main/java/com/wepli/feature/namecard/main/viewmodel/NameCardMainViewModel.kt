package com.wepli.feature.namecard.main.viewmodel

import base.BaseMviViewModel
import com.wepli.feature.namecard.main.mvi.NameCardMainEffect
import com.wepli.feature.namecard.main.mvi.NameCardMainIntent
import com.wepli.feature.namecard.main.mvi.NameCardMainUiState
import com.wepli.shared.feature.uimodel.user.UserUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import repository.user.UserRepository
import javax.inject.Inject

@HiltViewModel
class NameCardMainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseMviViewModel<NameCardMainUiState, NameCardMainEffect, NameCardMainIntent>(
    initialState = NameCardMainUiState(),
) {
    init {
        getUser()
    }

    override fun processIntent(intent: NameCardMainIntent) {

    }

    private fun getUser() = launch {
        userRepository.getUser()?.let {
            updateState { copy(user = UserUiData.fromDomain(it)) }
        }
    }
}