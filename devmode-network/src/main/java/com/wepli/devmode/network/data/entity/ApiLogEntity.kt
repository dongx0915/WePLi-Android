package com.wepli.devmode.network.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.wepli.devmode.network.data.model.ApiLog
import com.wepli.devmode.network.data.model.ApiMethod

@Entity(
    tableName = "ApiLogs",
    indices = [
        Index(
            value = ["id"],
            orders = [Index.Order.DESC]
        )
    ]
)
data class ApiLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val method: ApiMethod,
    val baseUrlType: String,
    val baseUrl: String,
    val url: String,
    val requestHeaders: Map<String, String>,
    val requestBody: String,
    val responseCode: Int,
    val responseBody: String,
    val startTime: Long,
    val durationMs: Long = System.currentTimeMillis() - startTime
)

fun ApiLogEntity.toDomain(): ApiLog {
    return ApiLog(
        id = id,
        method = method,
        baseUrlType = baseUrlType,
        baseUrl = baseUrl,
        url = url,
        requestHeaders = requestHeaders,
        requestBody = requestBody,
        responseCode = responseCode,
        responseBody = responseBody,
        startTime = startTime,
        durationMs = durationMs
    )
}

fun ApiLog.toEntity(): ApiLogEntity {
    return ApiLogEntity(
        id = 0,
        method = method,
        baseUrlType = baseUrlType,
        baseUrl = baseUrl,
        url = url,
        requestHeaders = requestHeaders,
        requestBody = requestBody,
        responseCode = responseCode,
        responseBody = responseBody,
        startTime = startTime,
        durationMs = durationMs
    )
}
