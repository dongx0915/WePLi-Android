package com.wepli.relaylist.detail.mvi

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.shared.feature.uimodel.relaylist.RelaylistUiData


data class RelaylistDetailUiState(
    val relaylist: RelaylistUiData = RelaylistUiData(),
    val remainingTime: Long = 0L,
) : UiState

interface RelaylistDetailEffect : SideEffect {
}

interface RelaylistDetailIntent : Intent {
    data class LoadRelaylist(val relaylistId: Int) : RelaylistDetailIntent
}