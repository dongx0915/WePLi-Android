package com.wepli.data.datastore.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DataStorePrefDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStorePrefDataSource {

    override suspend fun getInt(key: String, defaultValue: Int): Int {
        return try {
            dataStore.data.first()[intPreferencesKey(key)] ?: defaultValue
        } catch (e: Exception) {
            defaultValue
        }
    }

    override suspend fun setInt(key: String, value: Int) {
        try {
            dataStore.edit { preferences -> preferences[intPreferencesKey(key)] = value }
        } catch (e: Exception) {
            // handle exception
        }
    }

    override suspend fun getLong(key: String, defaultValue: Long): Long {
        return try {
            dataStore.data.first()[longPreferencesKey(key)] ?: defaultValue
        } catch (e: Exception) {
            defaultValue
        }
    }

    override suspend fun setLong(key: String, value: Long) {
        try {
            dataStore.edit { preferences -> preferences[longPreferencesKey(key)] = value }
        } catch (e: Exception) {
            // handle exception
        }
    }

    override suspend fun getFloat(key: String, defaultValue: Float): Float {
        return try {
            dataStore.data.first()[floatPreferencesKey(key)] ?: defaultValue
        } catch (e: Exception) {
            defaultValue
        }
    }

    override suspend fun setFloat(key: String, value: Float) {
        try {
            dataStore.edit { preferences -> preferences[floatPreferencesKey(key)] = value }
        } catch (e: Exception) {
            // handle exception
        }
    }

    override suspend fun getDouble(key: String, defaultValue: Double): Double {
        return try {
            dataStore.data.first()[doublePreferencesKey(key)] ?: defaultValue
        } catch (e: Exception) {
            defaultValue
        }
    }

    override suspend fun setDouble(key: String, value: Double) {
        try {
            dataStore.edit { preferences -> preferences[doublePreferencesKey(key)] = value }
        } catch (e: Exception) {
            // handle exception
        }
    }

    override suspend fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return try {
            dataStore.data.first()[booleanPreferencesKey(key)] ?: defaultValue
        } catch (e: Exception) {
            defaultValue
        }
    }

    override suspend fun setBoolean(key: String, value: Boolean) {
        try {
            dataStore.edit { preferences -> preferences[booleanPreferencesKey(key)] = value }
        } catch (e: Exception) {
            // handle exception
        }
    }

    override suspend fun getString(key: String, defaultValue: String): String {
        return try {
            dataStore.data.first()[stringPreferencesKey(key)] ?: defaultValue
        } catch (e: Exception) {
            defaultValue
        }
    }

    override suspend fun setString(key: String, value: String) {
        try {
            dataStore.edit { preferences -> preferences[stringPreferencesKey(key)] = value }
        } catch (e: Exception) {
            // handle exception
        }
    }
}