package com.wepli.mypage.devmode.network.detail.viewmodel

import androidx.lifecycle.viewModelScope
import base.BaseMviViewModel
import base.Intent
import base.SideEffect
import base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import debug.model.ApiLog
import debug.repository.DebugApiLogRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


data class NetworkLogDetailState(
    val apiLog: ApiLog? = null
) : UiState

sealed interface NetworkLogDetailEffect : SideEffect

sealed interface NetworkLogDetailIntent : Intent {
    data class InitApiLog(val apiLogId: Int) : NetworkLogDetailIntent
}

@HiltViewModel
class NetworkLogDetailViewModel @Inject constructor(
    apiLogRepository: DebugApiLogRepository
) : BaseMviViewModel<NetworkLogDetailState, NetworkLogDetailEffect, NetworkLogDetailIntent>(
    initialState = NetworkLogDetailState()
) {

    private val apiLogs: StateFlow<List<ApiLog>> = apiLogRepository.logs.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    override fun processIntent(intent: NetworkLogDetailIntent) {
        when (intent) {
            is NetworkLogDetailIntent.InitApiLog -> {
                updateApiLog(intent.apiLogId)
            }
        }
    }

    private fun updateApiLog(apiLogId: Int) = intent {
        launch {
            apiLogs.collect { logs ->
                logs.find { it.id == apiLogId }
                    ?.let {
                        updateState { copy(apiLog = it) }
                    }
            }
        }
    }
}