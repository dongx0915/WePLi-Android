package com.wepli.data.network.apilog.datasource

import com.wepli.data.db.devmode.dao.ApiLogDao
import com.wepli.data.db.devmode.entity.ApiLogEntity
import javax.inject.Inject

class DebugApiLogLocalDatasourceImpl @Inject constructor(
    private val apiLogDao: ApiLogDao
): DebugApiLogLocalDatasource {

    override suspend fun getLogs(count: Int): List<ApiLogEntity> {
        return apiLogDao.getLogs(count)
    }

    override suspend fun getCount(): Int {
        return apiLogDao.getCount()
    }

    override suspend fun insertLog(log: ApiLogEntity) {
        apiLogDao.insertLogWithCleanUp(log = log, maxLogs = 50, threshold = 70)
    }
    
    override suspend fun deleteOldLogs(limit: Int) {
        apiLogDao.deleteOldLogs(limit)
    }

    override suspend fun findLogById(id: Int): ApiLogEntity? {
        return apiLogDao.findLogById(id)
    }
}