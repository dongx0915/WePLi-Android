package com.wepli.data.network.apilog

import debug.model.ApiLog
import debug.repository.DebugApiLogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class DebugApiLogRepositoryImpl @Inject constructor() : DebugApiLogRepository {
    private val _logs: MutableStateFlow<List<ApiLog>> = MutableStateFlow(emptyList())
    override val logs: StateFlow<List<ApiLog>>
        get() = _logs.asStateFlow()

    override fun addLog(log: ApiLog) {
        val updatedLogs = (_logs.value + log).takeLast(20)

        _logs.value = updatedLogs
    }
}