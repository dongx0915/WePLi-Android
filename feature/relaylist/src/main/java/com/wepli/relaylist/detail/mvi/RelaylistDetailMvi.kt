package com.wepli.relaylist.detail.mvi

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.shared.feature.uimodel.relaylist.RelaylistUiData


data class RelaylistDetailUiState(
    val relaylist: RelaylistUiData = RelaylistUiData(),
) : UiState

interface RelaylistDetailEffect : SideEffect {
}

interface RelaylistDetailIntent : Intent