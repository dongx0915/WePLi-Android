package com.wepli.data.network.apilog

import com.wepli.data.devmode.entity.toDomain
import com.wepli.data.devmode.entity.toEntity
import com.wepli.data.network.apilog.datasource.DebugApiLogLocalDatasourceImpl
import com.wepli.domain.devmode.model.ApiLog
import com.wepli.domain.devmode.repository.DebugApiLogRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DebugApiLogRepositoryImpl @Inject constructor(
    private val apiLogDatasource: DebugApiLogLocalDatasourceImpl
) : DebugApiLogRepository {

    companion object {
        private const val MAX_LOGS = 50
        private const val CLEANUP_THRESHOLD = 70
    }

    override suspend fun getLogs(count: Int): List<ApiLog> {
        return apiLogDatasource.getLogs(count).map { it.toDomain() }
    }

    override suspend fun insertLog(log: ApiLog) {
        apiLogDatasource.insertLog(log.toEntity())
        
        val currentCount = apiLogDatasource.getCount()
        if (currentCount >= CLEANUP_THRESHOLD) {
            apiLogDatasource.deleteOldLogs(MAX_LOGS)
        }
    }

    override suspend fun findLogById(logId: Int): ApiLog? {
        return apiLogDatasource.findLogById(logId)?.toDomain()
    }
}