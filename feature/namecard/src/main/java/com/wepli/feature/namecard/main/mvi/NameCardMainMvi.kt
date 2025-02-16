package com.wepli.feature.namecard.main.mvi

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.shared.feature.uimodel.user.UserUiData

data class NameCardMainUiState(
    val user: UserUiData = UserUiData(),
) : UiState

interface NameCardMainEffect : SideEffect {

}

interface NameCardMainIntent : Intent {

}