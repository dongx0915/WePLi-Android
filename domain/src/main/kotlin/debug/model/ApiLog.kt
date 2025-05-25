package debug.model

data class ApiLog(
    val method: String,
    val url: String,
    val requestHeaders: String,
    val requestBody: String,
    val responseCode: Int,
    val responseBody: String,
    val durationMs: Long
)