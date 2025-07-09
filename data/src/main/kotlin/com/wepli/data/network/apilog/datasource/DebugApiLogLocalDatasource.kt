package com.wepli.data.network.apilog.datasource

import com.wepli.data.db.devmode.entity.ApiLogEntity
import kotlinx.coroutines.flow.Flow

interface DebugApiLogLocalDatasource {
    val logs: Flow<List<ApiLogEntity>>

    suspend fun insertLog(log: ApiLogEntity)
}