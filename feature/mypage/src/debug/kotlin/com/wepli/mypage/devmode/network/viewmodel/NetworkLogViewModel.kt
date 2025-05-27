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
        collectApiLog()
    }

    override fun processIntent(intent: NetworkLogIntent) {
        when (intent) {
            is NetworkLogIntent.SelectTag -> {}
        }
    }

    private fun collectApiLog() = intent {
        viewModelScope.launch {
            apiLogRepository.logs.collect {
                val currentTag = state.selectedTag
                val filteredLogs = it
                    .filter { log ->
                        currentTag == ApiMethodUiTag.ALL || log.method.name == currentTag.name
                    }
                    .sortedByDescending { it.startTime }

                updateState { copy(apiLog = filteredLogs) }
            }
        }
    }
}