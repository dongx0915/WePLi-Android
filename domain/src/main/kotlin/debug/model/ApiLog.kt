package debug.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

data class ApiLog(
    val id: String = UUID.randomUUID().toString(),
    val method: ApiMethod,
    val baseUrlType: String,
    val baseUrl: String,
    val url: String,
    val requestHeaders: String,
    val requestBody: String,
    val responseCode: Int,
    val responseBody: String,
    val startTime: Long,
    val durationMs: Long = System.currentTimeMillis() - startTime
) {
    fun formattedStartTime(): String {
        val sdf = SimpleDateFormat("a hh:mm:ss", Locale.KOREAN)
        return sdf.format(Date(startTime))
    }
}

enum class ApiMethod {
    GET,
    POST,
    PUT,
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