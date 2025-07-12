package com.wepli.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.wepli.data.db.devmode.dao.ApiLogDao
import com.wepli.data.db.devmode.entity.ApiLogEntity
import kotlinx.serialization.json.Json

// TypeConverter 클래스 생성
internal class Converters {
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
@TypeConverters(Converters::class)
abstract class WePLiDatabase : RoomDatabase() {

    abstract fun apiLogDao(): ApiLogDao

    companion object {
        const val DATABASE_NAME = "wepli_database"

        fun create(context: Context): WePLiDatabase {
            return Room.databaseBuilder(
                context = context,
                klass = WePLiDatabase::class.java,
                name = DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}