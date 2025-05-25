package debug.repository

import debug.model.ApiLog
import kotlinx.coroutines.flow.StateFlow

interface DebugApiLogRepository {
    val logs: StateFlow<List<ApiLog>>

    fun addLog(log: ApiLog)
}