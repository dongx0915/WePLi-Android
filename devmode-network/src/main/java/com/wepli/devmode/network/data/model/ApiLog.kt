package com.wepli.devmode.network.data.model

import androidx.compose.runtime.Stable
import java.net.URLDecoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ApiLogMeta(
    val method: ApiMethod,
    val baseUrlType: String,
    val baseUrl: String,
    val host: String,
    val scheme: String,
    val url: String,
    val protocol: String,
    val errorMessage: String?,
    val startTime: Long,
    val durationMs: Long,
) {
    val decodedUrl: String by lazy {
        try {
            URLDecoder.decode(url, "UTF-8")
        } catch (e: Exception) {
            url
        }
    }

    fun formattedStartTime(): String {
        val sdf = SimpleDateFormat("a hh:mm:ss", Locale.KOREAN)
        return sdf.format(Date(startTime))
    }
}

data class ApiLogRequest(
    val headers: Map<String, String>,
    val headersSize: Long,
    val body: String,
    val bodySize: Long,
    val contentType: String?,
)

data class ApiLogResponse(
    val code: Int,
    val message: String,
    val headers: Map<String, String>,
    val headersSize: Long,
    val body: String,
    val bodySize: Long,
    val contentType: String?,
    val tlsVersion: String?,
    val cipherSuite: String?,
) {
    val formattedBodySize: String
        get() = when {
            bodySize < 0L -> "unknown"
            bodySize < 1024L -> "${bodySize} B"
            bodySize < 1024L * 1024L -> "${"%.1f".format(bodySize / 1024.0)} KB"
            else -> "${"%.1f".format(bodySize / (1024.0 * 1024.0))} MB"
        }

    companion object {
        fun default(): ApiLogResponse = ApiLogResponse(
            code = 0,
            message = "",
            headers = emptyMap(),
            headersSize = -1L,
            body = "",
            bodySize = -1L,
            contentType = null,
            tlsVersion = null,
            cipherSuite = null,
        )
    }
}

@Stable
data class ApiLog(
    val id: Int = 0,
    val meta: ApiLogMeta,
    val request: ApiLogRequest,
    val response: ApiLogResponse,
)

enum class ApiMethod {
    GET,
    POST,
    PUT,
    PATCH,
    DELETE,
    UNKNOWN;

    companion object {
        fun fromString(method: String): ApiMethod {
            return try {
                valueOf(method.uppercase(Locale.getDefault()))
            } catch (e: IllegalArgumentException) {
                UNKNOWN
            }
        }
    }
}