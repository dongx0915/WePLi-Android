package com.wepli.data.devmode.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.wepli.data.devmode.entity.ApiLogEntity

@Dao
interface ApiLogDao {

    @Query("SELECT * FROM ApiLogs ORDER BY id DESC LIMIT :count")
    suspend fun getLogs(count: Int): List<ApiLogEntity>

    @Query("SELECT COUNT(*) FROM ApiLogs")
    suspend fun getCount(): Int

    @Insert
    suspend fun insert(log: ApiLogEntity)

    @Transaction
    suspend fun insertLogWithCleanUp(log: ApiLogEntity, maxLogs: Int, threshold: Int) {
        insert(log)
        val count = getCount()
        if (count >= threshold) {
            deleteOldLogs(maxLogs)
        }
    }

    @Query("DELETE FROM ApiLogs WHERE id NOT IN (SELECT id FROM ApiLogs ORDER BY id DESC LIMIT :limit)")
    suspend fun deleteOldLogs(limit: Int)

    @Query("SELECT * FROM ApiLogs WHERE id = :id")
    suspend fun findLogById(id: Int): ApiLogEntity?
}