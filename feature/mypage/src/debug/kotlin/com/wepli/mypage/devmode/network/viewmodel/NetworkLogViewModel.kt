package com.wepli.mypage.devmode.network.viewmodel

import base.BaseMviViewModel
import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.mypage.devmode.network.enums.ApiMethodUiModel
import debug.model.ApiLog


data class NetworkLogState(
    val apiLog: List<ApiLog> = emptyList(),
    val selectedTag: ApiMethodUiModel = ApiMethodUiModel.ALL,
): UiState

sealed interface NetworkLogEffect : SideEffect {
}

sealed interface NetworkLogIntent : Intent {

}

class NetworkLogViewModel : BaseMviViewModel<NetworkLogState, NetworkLogEffect, NetworkLogIntent>(
    initialState = NetworkLogState()
) {
    override fun processIntent(intent: NetworkLogIntent) {

    }


}