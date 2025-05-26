package debug.model

import java.util.Locale

data class ApiLog(
    val method: ApiMethod,
    val url: String,
    val requestHeaders: String,
    val requestBody: String,
    val responseCode: Int,
    val responseBody: String,
    val durationMs: Long
)

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