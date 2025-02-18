package com.wepli.feature.namecard.result.mvi

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.shared.feature.uimodel.namecard.NameCardUiData
import com.wepli.shared.feature.uimodel.user.UserUiData

data class NameCardResultUiState(
    val user: UserUiData = UserUiData(),
    val nameCardInfo: NameCardUiData = NameCardUiData(),
) : UiState

interface NameCardResultEffect : SideEffect

interface NameCardResultIntent : Intent