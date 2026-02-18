package com.wepli.devmode.network.data.model

import java.net.URLDecoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ApiLog(
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