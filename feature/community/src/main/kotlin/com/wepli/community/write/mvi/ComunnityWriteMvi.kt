package com.wepli.community.write.mvi

import base.Intent
import base.SideEffect
import base.UiState

data class CommunityWriteUiState(
    val title: String = "",
    val contents: String = "",
) : UiState

interface CommunityWriteEffect : SideEffect

interface CommunityWriteIntent : Intent