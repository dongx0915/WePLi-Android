package com.wepli.community.write.mvi

import base.Intent
import base.SideEffect
import base.UiState

data class CommunityWriteUiState(
    val title: String = "",
    val contents: String = "",
    val isTitleLengthExceeded: Boolean = false,
    val isContentsLengthExceeded: Boolean = false,
) : UiState

interface CommunityWriteEffect : SideEffect

interface CommunityWriteIntent : Intent {
    data class UpdateTitle(val title: String, val maxLength: Int) : CommunityWriteIntent
    data class UpdateContents(val contents: String, val maxLength: Int) : CommunityWriteIntent
}