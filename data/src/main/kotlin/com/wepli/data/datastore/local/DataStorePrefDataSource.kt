package com.wepli.data.datastore.local

interface DataStorePrefDataSource {

    suspend fun getInt(key: String, defaultValue: Int): Int
    suspend fun setInt(key: String, value: Int)

    suspend fun getLong(key: String, defaultValue: Long): Long
    suspend fun setLong(key: String, value: Long)

    suspend fun getFloat(key: String, defaultValue: Float): Float
    suspend fun setFloat(key: String, value: Float)

    suspend fun getDouble(key: String, defaultValue: Double): Double
    suspend fun setDouble(key: String, value: Double)

    suspend fun getBoolean(key: String, defaultValue: Boolean): Boolean
    suspend fun setBoolean(key: String, value: Boolean)

    suspend fun getString(key: String, defaultValue: String): String
    suspend fun setString(key: String, value: String)
}