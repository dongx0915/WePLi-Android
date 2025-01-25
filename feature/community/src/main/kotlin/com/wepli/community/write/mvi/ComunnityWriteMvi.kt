package com.wepli.community.write.mvi

import base.Intent
import base.SideEffect
import base.UiState

data class CommunityWriteUiState(
    val title: FieldState = FieldState(),
    val contents: FieldState = FieldState()
) : UiState {

    data class FieldState(
        val text: String = "",
        val isLengthExceeded: Boolean = false
    )
}

interface CommunityWriteEffect : SideEffect

interface CommunityWriteIntent : Intent {
    data class UpdateTitle(val title: String, val maxLength: Int) : CommunityWriteIntent
    data class UpdateContents(val contents: String, val maxLength: Int) : CommunityWriteIntent
}