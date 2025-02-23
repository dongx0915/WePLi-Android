package com.wepli.feature.photocard.result.mvi

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.shared.feature.uimodel.namecard.PhotoCardUiData
import com.wepli.shared.feature.uimodel.user.UserUiData

data class PhotoCardResultUiState(
    val user: UserUiData = UserUiData(),
    val photoCardInfo: PhotoCardUiData = PhotoCardUiData(),
    val isShownShareBottomSheet: Boolean = false
) : UiState

interface PhotoCardResultEffect : SideEffect

interface PhotoCardResultIntent : Intent {
    data class Initialize(val photoCardInfo: PhotoCardUiData) : PhotoCardResultIntent
    data class ShowShareBottomSheet(val isShown: Boolean) : PhotoCardResultIntent
}