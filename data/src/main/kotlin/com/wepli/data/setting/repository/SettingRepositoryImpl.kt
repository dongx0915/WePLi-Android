package com.wepli.data.setting.repository

import com.wepli.data.datastore.DataStoreKey
import com.wepli.data.datastore.local.DataStorePrefDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import repository.setting.SettingRepository
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val dataStorePrefDataSource: DataStorePrefDataSource
) : SettingRepository {

    override suspend fun setEnableScreenNameViewer(enabled: Boolean) = withContext(Dispatchers.IO) {
        dataStorePrefDataSource.setBoolean(DataStoreKey.SCREEN_NAME_VIEWER_ENABLED, enabled)
    }

    override suspend fun isEnableScreenNameViewer(): Boolean = withContext(Dispatchers.IO) {
        dataStorePrefDataSource.getBoolean(
            key = DataStoreKey.SCREEN_NAME_VIEWER_ENABLED,
            defaultValue = false
        )
    }
}