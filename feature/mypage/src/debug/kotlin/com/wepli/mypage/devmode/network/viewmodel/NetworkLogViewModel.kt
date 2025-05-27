package com.wepli.mypage.devmode.network.viewmodel

import androidx.lifecycle.viewModelScope
import base.BaseMviViewModel
import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.mypage.devmode.network.enums.ApiMethodUiTag
import dagger.hilt.android.lifecycle.HiltViewModel
import debug.model.ApiLog
import debug.repository.DebugApiLogRepository
import kotlinx.coroutines.launch
import javax.inject.Inject


data class NetworkLogState(
    val apiLog: List<ApiLog> = emptyList(),
    val selectedTag: ApiMethodUiTag = ApiMethodUiTag.ALL,
): UiState

sealed interface NetworkLogEffect : SideEffect {
}

sealed interface NetworkLogIntent : Intent {
    data class SelectTag(val tag: ApiMethodUiTag) : NetworkLogIntent
}

@HiltViewModel
class NetworkLogViewModel @Inject constructor(
    private val apiLogRepository: DebugApiLogRepository
) : BaseMviViewModel<NetworkLogState, NetworkLogEffect, NetworkLogIntent>(
    initialState = NetworkLogState()
) {

    init {
        viewModelScope.launch {
            apiLogRepository.logs.collect {
                updateState { copy(apiLog = it.sortedByDescending { it.startTime }) }
            }
        }
    }

    override fun processIntent(intent: NetworkLogIntent) {

    }
}