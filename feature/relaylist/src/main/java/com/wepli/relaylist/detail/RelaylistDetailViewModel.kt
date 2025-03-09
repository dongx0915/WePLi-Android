package com.wepli.relaylist.detail

import base.BaseMviViewModel
import com.wepli.relaylist.detail.mvi.RelaylistDetailEffect
import com.wepli.relaylist.detail.mvi.RelaylistDetailIntent
import com.wepli.relaylist.detail.mvi.RelaylistDetailUiState
import com.wepli.shared.feature.mock.relaylistUiMockData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RelaylistDetailViewModel @Inject constructor() : BaseMviViewModel<RelaylistDetailUiState, RelaylistDetailEffect, RelaylistDetailIntent>(
    initialState = RelaylistDetailUiState()
) {
    init {
        updateState {
            copy(relaylist = relaylistUiMockData.random())
        }
    }

    override fun processIntent(intent: RelaylistDetailIntent) {
        // TODO("Not yet implemented")
    }
}