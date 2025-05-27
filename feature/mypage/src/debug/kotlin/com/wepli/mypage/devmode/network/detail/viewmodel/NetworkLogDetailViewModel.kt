package com.wepli.mypage.devmode.network.detail.viewmodel

import base.BaseMviViewModel
import base.Intent
import base.SideEffect
import base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import debug.model.ApiLog
import debug.repository.DebugApiLogRepository
import javax.inject.Inject


data class NetworkLogDetailState(
    val apiLog: ApiLog? = null
) : UiState

sealed interface NetworkLogDetailEffect : SideEffect {
}

sealed interface NetworkLogDetailIntent : Intent {

}

@HiltViewModel
class NetworkLogDetailViewModel @Inject constructor(
    private val apiLogRepository: DebugApiLogRepository
) : BaseMviViewModel<NetworkLogDetailState, NetworkLogDetailEffect, NetworkLogDetailIntent>(
    initialState = NetworkLogDetailState()
) {
    override fun processIntent(intent: NetworkLogDetailIntent) {
        TODO("Not yet implemented")
    }

}