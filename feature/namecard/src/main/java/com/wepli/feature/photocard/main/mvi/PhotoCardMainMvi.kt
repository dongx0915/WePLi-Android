package com.wepli.feature.photocard.main.mvi

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.shared.feature.uimodel.namecard.PhotoCardUiData
import com.wepli.shared.feature.uimodel.user.UserUiData

data class PhotoCardMainUiState(
    val user: UserUiData = UserUiData(),
    val photoCardInfo: PhotoCardUiData = PhotoCardUiData(),
) : UiState

interface PhotoCardMainEffect : SideEffect {

}

interface PhotoCardMainIntent : Intent {

}