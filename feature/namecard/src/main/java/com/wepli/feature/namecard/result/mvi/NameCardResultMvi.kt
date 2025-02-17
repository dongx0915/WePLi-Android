package com.wepli.feature.namecard.result.mvi

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.shared.feature.uimodel.user.UserUiData

data class NameCardResultUiState(
    val user: UserUiData = UserUiData(),
) : UiState

interface NameCardResultEffect : SideEffect

interface NameCardResultIntent : Intent