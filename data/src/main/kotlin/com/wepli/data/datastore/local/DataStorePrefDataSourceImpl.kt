package com.wepli.data.datastore.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.wepli.data.di.qualifier.WepliDataStore
import extensions.parseFromJson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStorePrefDataSourceImpl @Inject constructor(
    @WepliDataStore private val dataStore: DataStore<Preferences>
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

    override suspend fun removeInt(key: String) {
        try {
            dataStore.edit { preferences -> preferences.remove(intPreferencesKey(key)) }
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

    override suspend fun removeLong(key: String) {
        try {
            dataStore.edit { preferences -> preferences.remove(longPreferencesKey(key)) }
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

    override suspend fun removeFloat(key: String) {
        try {
            dataStore.edit { preferences -> preferences.remove(floatPreferencesKey(key)) }
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

    override suspend fun removeDouble(key: String) {
        try {
            dataStore.edit { preferences -> preferences.remove(doublePreferencesKey(key)) }
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

    override suspend fun removeBoolean(key: String) {
        try {
            dataStore.edit { preferences -> preferences.remove(booleanPreferencesKey(key)) }
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

    override suspend fun removeString(key: String) {
        try {
            dataStore.edit { preferences -> preferences.remove(stringPreferencesKey(key)) }
        } catch (e: Exception) {
            // handle exception
        }
    }

    // Flow
    override fun getIntFlow(key: String, defaultValue: Int): Flow<Int> {
        return getFlow(intPreferencesKey(key), defaultValue)
    }

    override fun getLongFlow(key: String, defaultValue: Long): Flow<Long> {
        return getFlow(longPreferencesKey(key), defaultValue)
    }

    override fun getFloatFlow(key: String, defaultValue: Float): Flow<Float> {
        return getFlow(floatPreferencesKey(key), defaultValue)
    }

    override fun getDoubleFlow(key: String, defaultValue: Double): Flow<Double> {
        return getFlow(doublePreferencesKey(key), defaultValue)
    }

    override fun getBooleanFlow(key: String, defaultValue: Boolean): Flow<Boolean> {
        return getFlow(booleanPreferencesKey(key), defaultValue)
    }

    override fun getStringFlow(key: String, defaultValue: String): Flow<String> {
        return getFlow(stringPreferencesKey(key), defaultValue)
    }

    override fun <T> getObjectFlow(key: String, clazz: Class<T>): Flow<T?> = dataStore.data
        .catch { e ->
            Log.e("DataStore", "Error reading data", e)
            if (e is IOException) emit(emptyPreferences()) else throw e
        }.map { prefs ->
            val json = prefs[stringPreferencesKey(key)]
            json?.parseFromJson(clazz)
        }

    private fun <T> getFlow(
        key: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T> {
        return dataStore.data
            .catch { e ->
                Log.e("DataStore", "Error reading key: $key", e)
                if (e is IOException) emit(emptyPreferences()) else throw e
            }
            .map { prefs -> prefs[key] ?: defaultValue }
    }
}