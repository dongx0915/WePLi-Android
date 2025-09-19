package com.wepli.data.network.apilog.datasource

import com.wepli.data.devmode.entity.ApiLogEntity

interface DebugApiLogLocalDatasource {

    suspend fun getLogs(count: Int): List<ApiLogEntity>

    suspend fun getCount(): Int

    suspend fun insertLog(log: ApiLogEntity)

    suspend fun deleteOldLogs(limit: Int)

    suspend fun findLogById(id: Int): ApiLogEntity?
}