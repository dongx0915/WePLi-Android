package com.wepli.feature.photocard.main.mvi

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.shared.feature.uimodel.namecard.PhotoCardUiData
import com.wepli.shared.feature.uimodel.user.UserUiData

data class NameCardMainUiState(
    val user: UserUiData = UserUiData(),
    val nameCardInfo: PhotoCardUiData = PhotoCardUiData(),
) : UiState

interface NameCardMainEffect : SideEffect {

}

interface NameCardMainIntent : Intent {

}