package com.wepli.data.datastore

import com.wepli.data.datastore.local.DataStorePrefDataSource
import javax.inject.Inject

class DataStorePrefRepositoryImpl @Inject constructor(
    private val dataStorePrefDataSource: DataStorePrefDataSource
) : DataStorePrefRepository {

    override suspend fun getInt(key: String, defaultValue: Int): Int {
        return dataStorePrefDataSource.getInt(key, defaultValue)
    }

    override suspend fun setInt(key: String, value: Int) {
        dataStorePrefDataSource.setInt(key, value)
    }

    override suspend fun getLong(key: String, defaultValue: Long): Long {
        return dataStorePrefDataSource.getLong(key, defaultValue)
    }

    override suspend fun setLong(key: String, value: Long) {
        dataStorePrefDataSource.setLong(key, value)
    }

    override suspend fun getFloat(key: String, defaultValue: Float): Float {
        return dataStorePrefDataSource.getFloat(key, defaultValue)
    }

    override suspend fun setFloat(key: String, value: Float) {
        dataStorePrefDataSource.setFloat(key, value)
    }

    override suspend fun getDouble(key: String, defaultValue: Double): Double {
        return dataStorePrefDataSource.getDouble(key, defaultValue)
    }

    override suspend fun setDouble(key: String, value: Double) {
        dataStorePrefDataSource.setDouble(key, value)
    }

    override suspend fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return dataStorePrefDataSource.getBoolean(key, defaultValue)
    }

    override suspend fun setBoolean(key: String, value: Boolean) {
        dataStorePrefDataSource.setBoolean(key, value)
    }

    override suspend fun getString(key: String, defaultValue: String): String {
        return dataStorePrefDataSource.getString(key, defaultValue)
    }

    override suspend fun setString(key: String, value: String) {
        dataStorePrefDataSource.setString(key, value)
    }
}