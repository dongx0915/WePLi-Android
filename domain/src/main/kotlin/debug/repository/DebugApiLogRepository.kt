package debug.repository

import debug.model.ApiLog
import kotlinx.coroutines.flow.Flow

interface DebugApiLogRepository {
    val logs: Flow<List<ApiLog>>

    suspend fun insertLog(log: ApiLog)
}