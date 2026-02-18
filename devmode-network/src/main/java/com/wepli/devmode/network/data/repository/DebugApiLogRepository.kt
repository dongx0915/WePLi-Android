package com.wepli.devmode.network.data.repository

import com.wepli.devmode.network.data.model.ApiLog

interface DebugApiLogRepository {
    suspend fun getLogs(count: Int): List<ApiLog>

    suspend fun insertLog(log: ApiLog)

    suspend fun findLogById(logId: Int): ApiLog?
}