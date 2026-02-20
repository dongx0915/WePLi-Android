package com.wepli.devmode.network.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.wepli.devmode.network.data.model.ApiLog
import com.wepli.devmode.network.data.model.ApiLogMeta
import com.wepli.devmode.network.data.model.ApiLogRequest
import com.wepli.devmode.network.data.model.ApiLogResponse
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

    /* meta */
    val method: ApiMethod,
    val url: String,
    val baseUrlType: String,
    val baseUrl: String,
    val host: String = "",
    val scheme: String = "unknown",
    val protocol: String = "unknown",
    val errorMessage: String? = null,
    val startTime: Long,
    val durationMs: Long = System.currentTimeMillis() - startTime,

    /* request */
    val requestHeaders: Map<String, String>,
    val requestHeadersSize: Long = -1L,
    val requestBody: String,
    val requestBodySize: Long = -1L,
    val requestContentType: String? = null,

    /* response */
    val responseCode: Int,
    val responseMessage: String = "",
    val responseHeaders: Map<String, String> = emptyMap(),
    val responseHeadersSize: Long = -1L,
    val responseBody: String,
    val responseBodySize: Long = -1L,
    val responseContentType: String? = null,
    val responseTlsVersion: String? = null,
    val responseCipherSuite: String? = null,
)

fun ApiLogEntity.toDomain(): ApiLog {
    return ApiLog(
        id = id,
        meta = ApiLogMeta(
            method = method,
            baseUrlType = baseUrlType,
            baseUrl = baseUrl,
            host = host,
            scheme = scheme,
            url = url,
            protocol = protocol,
            errorMessage = errorMessage,
            startTime = startTime,
            durationMs = durationMs,
        ),
        request = ApiLogRequest(
            headers = requestHeaders,
            headersSize = requestHeadersSize,
            body = requestBody,
            bodySize = requestBodySize,
            contentType = requestContentType,
        ),
        response = ApiLogResponse(
            code = responseCode,
            message = responseMessage,
            headers = responseHeaders,
            headersSize = responseHeadersSize,
            body = responseBody,
            bodySize = responseBodySize,
            contentType = responseContentType,
            tlsVersion = responseTlsVersion,
            cipherSuite = responseCipherSuite,
        ),
    )
}

fun ApiLog.toEntity(): ApiLogEntity {
    return ApiLogEntity(
        id = 0,
        /* meta */
        method = meta.method,
        baseUrlType = meta.baseUrlType,
        baseUrl = meta.baseUrl,
        host = meta.host,
        scheme = meta.scheme,
        url = meta.url,
        protocol = meta.protocol,
        errorMessage = meta.errorMessage,
        startTime = meta.startTime,
        durationMs = meta.durationMs,
        /* request */
        requestHeaders = request.headers,
        requestHeadersSize = request.headersSize,
        requestBody = request.body,
        requestBodySize = request.bodySize,
        requestContentType = request.contentType,
        /* response */
        responseCode = response.code,
        responseMessage = response.message,
        responseHeaders = response.headers,
        responseHeadersSize = response.headersSize,
        responseBody = response.body,
        responseBodySize = response.bodySize,
        responseContentType = response.contentType,
        responseTlsVersion = response.tlsVersion,
        responseCipherSuite = response.cipherSuite,
    )
}
