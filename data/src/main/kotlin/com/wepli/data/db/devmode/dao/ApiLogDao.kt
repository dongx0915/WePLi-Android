package com.wepli.data.db.devmode.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.wepli.data.db.devmode.entity.ApiLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ApiLogDao {

    @Query("SELECT * FROM ApiLogs")
    fun getAll(): Flow<List<ApiLogEntity>>

    @Insert
    suspend fun insert(log: ApiLogEntity)
}