package com.wepli.mypage.menus.devmode.network.main.viewmodel

import base.BaseMviViewModel
import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.mypage.menus.devmode.network.main.enums.ApiMethodUiTag
import dagger.hilt.android.lifecycle.HiltViewModel
import debug.model.ApiLog
import debug.repository.DebugApiLogRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


data class NetworkLogState(
    val maxApiLogs: Int = 50,
    val originApiLogs: List<ApiLog> = emptyList(),
    val filteredApiLogs: List<ApiLog> = emptyList(),
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
            is NetworkLogIntent.SelectTag -> updateSelectedTag(intent.tag)
        }
    }

    private fun collectApiLog() = intent {
        launch(Dispatchers.IO) {
            val sortedLogs = apiLogRepository
                .getLogs(state.maxApiLogs)
                .sortedByDescending { it.startTime }
            val filteredApiLogs = filterLogs(sortedLogs, state.selectedTag)

            // 이전과 같으면 생략
            if (state.originApiLogs == sortedLogs && state.filteredApiLogs == filteredApiLogs) {
                return@launch
            }

            updateState {
                copy(originApiLogs = sortedLogs, filteredApiLogs = filteredApiLogs)
            }
        }
    }

    private fun updateSelectedTag(tag: ApiMethodUiTag) = intent {
        if (tag == state.selectedTag) return@intent

        val filtered = filterLogs(state.originApiLogs, tag)
        updateState { copy(selectedTag = tag, filteredApiLogs = filtered) }
    }

    private fun filterLogs(logs: List<ApiLog>, tag: ApiMethodUiTag): List<ApiLog> {
        return if (tag == ApiMethodUiTag.ALL) {
            logs
        } else {
            logs.filter { it.method.name == tag.name }
        }
    }
}