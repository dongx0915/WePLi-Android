package com.wepli.domain.devmode.repository

import com.wepli.domain.devmode.model.ApiLog
import kotlinx.coroutines.flow.Flow

interface DebugApiLogRepository {
    suspend fun getLogs(count: Int): List<ApiLog>

    suspend fun insertLog(log: ApiLog)

    suspend fun findLogById(logId: Int): ApiLog?
}