package com.wepli.data.network.apilog

import com.wepli.data.db.devmode.entity.toDomain
import com.wepli.data.db.devmode.entity.toEntity
import com.wepli.data.network.apilog.datasource.DebugApiLogLocalDatasource
import debug.model.ApiLog
import debug.repository.DebugApiLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DebugApiLogRepositoryImpl @Inject constructor(
    private val apiLogDatasource: DebugApiLogLocalDatasource
) : DebugApiLogRepository {

    override val logs: Flow<List<ApiLog>>
        get() = apiLogDatasource.logs
            .map { it.map { entity -> entity.toDomain() } }

    override suspend fun insertLog(log: ApiLog) {
        apiLogDatasource.insertLog(log.toEntity())
    }
}