package com.wepli.data.db.devmode.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.wepli.data.db.devmode.entity.ApiLogEntity
import io.github.jan.supabase.postgrest.query.Count
import kotlinx.coroutines.flow.Flow

@Dao
interface ApiLogDao {

    @Query("SELECT * FROM ApiLogs ORDER BY id DESC LIMIT :count")
    suspend fun getLogs(count: Int): List<ApiLogEntity>

    @Query("SELECT COUNT(*) FROM ApiLogs")
    suspend fun getCount(): Int

    @Insert
    suspend fun insert(log: ApiLogEntity)

    @Query("DELETE FROM ApiLogs WHERE id NOT IN (SELECT id FROM ApiLogs ORDER BY id DESC LIMIT :limit)")
    suspend fun deleteOldLogs(limit: Int)

    @Query("SELECT * FROM ApiLogs WHERE id = :id")
    suspend fun findLogById(id: Int): ApiLogEntity?
}