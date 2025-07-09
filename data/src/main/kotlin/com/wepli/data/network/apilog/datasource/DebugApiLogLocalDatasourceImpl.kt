package com.wepli.data.network.apilog.datasource

import com.wepli.data.db.devmode.dao.ApiLogDao
import com.wepli.data.db.devmode.entity.ApiLogEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DebugApiLogLocalDatasourceImpl @Inject constructor(
    private val apiLogDao: ApiLogDao
): DebugApiLogLocalDatasource {

    override val logs: Flow<List<ApiLogEntity>>
        get() = apiLogDao.getAll()

    override suspend fun insertLog(log: ApiLogEntity) {
        apiLogDao.insert(log)
    }
}