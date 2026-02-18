package com.wepli.devmode.network.data.datasource

import com.wepli.devmode.network.data.entity.ApiLogEntity

interface DebugApiLogLocalDatasource {

    suspend fun getLogs(count: Int): List<ApiLogEntity>

    suspend fun getCount(): Int

    suspend fun insertLog(log: ApiLogEntity)

    suspend fun deleteOldLogs(limit: Int)

    suspend fun findLogById(id: Int): ApiLogEntity?
}
