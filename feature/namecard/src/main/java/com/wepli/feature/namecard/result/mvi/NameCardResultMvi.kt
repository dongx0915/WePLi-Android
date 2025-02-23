package com.wepli.feature.namecard.result.mvi

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.shared.feature.uimodel.namecard.PhotoCardUiData
import com.wepli.shared.feature.uimodel.user.UserUiData

data class NameCardResultUiState(
    val user: UserUiData = UserUiData(),
    val nameCardInfo: PhotoCardUiData = PhotoCardUiData(),
    val isShownShareBottomSheet: Boolean = false
) : UiState

interface NameCardResultEffect : SideEffect

interface NameCardResultIntent : Intent {
    data class Initialize(val nameCardInfo: PhotoCardUiData) : NameCardResultIntent
    data class ShowShareBottomSheet(val isShown: Boolean) : NameCardResultIntent
}