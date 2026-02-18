package com.wepli.devmode.network.presentation.main.viewmodel

import androidx.compose.runtime.Stable
import base.BaseMviViewModel
import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.devmode.network.data.model.ApiLog
import com.wepli.devmode.network.data.repository.DebugApiLogRepository
import com.wepli.devmode.network.presentation.main.enums.ApiMethodUiTag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


@Stable
data class NetworkLogState(
    val maxApiLogs: Int = 50,
    val originApiLogs: List<ApiLog> = emptyList(),
    val filteredApiLogs: Map<String, List<ApiLog>> = emptyMap(),
): UiState

sealed interface NetworkLogEffect : SideEffect

sealed interface NetworkLogIntent : Intent

@HiltViewModel
class NetworkLogViewModel @Inject constructor(
    private val apiLogRepository: DebugApiLogRepository
) : BaseMviViewModel<NetworkLogState, NetworkLogEffect, NetworkLogIntent>(
    initialState = NetworkLogState()
) {

    init {
        collectApiLog()
    }

    override fun processIntent(intent: NetworkLogIntent) = Unit

    private fun collectApiLog() = intent {
        launch(Dispatchers.IO) {
            val originApiLogs = apiLogRepository
                .getLogs(state.maxApiLogs)
                .sortedByDescending { it.startTime }

            val filteredApiLogs = mutableMapOf<String, List<ApiLog>>()
                .apply {
                    put(ApiMethodUiTag.ALL.name, originApiLogs)
                    putAll(originApiLogs.groupBy { it.method.name })
                }

            updateState {
                copy(originApiLogs = originApiLogs, filteredApiLogs = filteredApiLogs)
            }
        }
    }
}