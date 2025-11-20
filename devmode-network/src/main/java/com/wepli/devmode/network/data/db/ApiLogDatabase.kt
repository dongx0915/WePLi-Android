package com.wepli.devmode.network.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.wepli.devmode.network.data.dao.ApiLogDao
import com.wepli.devmode.network.data.entity.ApiLogEntity
import kotlinx.serialization.json.Json

internal class ApiLogConverters {
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    @TypeConverter
    fun fromStringMap(value: Map<String, String>): String {
        return json.encodeToString(value)
    }

    @TypeConverter
    fun toStringMap(value: String): Map<String, String> {
        return json.decodeFromString(value)
    }
}

@Database(
    entities = [
        ApiLogEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(ApiLogConverters::class)
abstract class ApiLogDatabase : RoomDatabase() {

    abstract fun apiLogDao(): ApiLogDao

    companion object {
        const val DATABASE_NAME = "api_log_database"

        fun create(context: Context): ApiLogDatabase {
            return Room.databaseBuilder(
                context = context,
                klass = ApiLogDatabase::class.java,
                name = DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
