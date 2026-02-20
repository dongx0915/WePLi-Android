package com.wepli.devmode.network.plugin.retrofit

import android.util.Log
import com.wepli.devmode.network.data.model.ApiLog
import com.wepli.devmode.network.data.model.ApiLogMeta
import com.wepli.devmode.network.data.model.ApiLogRequest
import com.wepli.devmode.network.data.model.ApiLogResponse
import com.wepli.devmode.network.data.model.ApiMethod
import com.wepli.devmode.network.data.model.BaseUrlMatcher
import com.wepli.devmode.network.data.repository.DebugApiLogRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import javax.inject.Inject

class ApiLogRetrofitInterceptor @Inject constructor(
    private val apiLogRepository: DebugApiLogRepository,
    private val baseUrlMatcher: BaseUrlMatcher
) : Interceptor {

    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val host = request.url.host
        val scheme = request.url.scheme
        val headersMap: Map<String, String> = request.headers.names().associateWith { name ->
            request.headers.values(name).joinToString(", ")
        }
        val requestHeadersSize = request.headers.byteCount()
        val requestBody = request.body?.let { body ->
            val buffer = Buffer()
            body.writeTo(buffer)
            buffer.readUtf8()
        } ?: ""
        val requestBodySize = request.body?.contentLength() ?: -1L
        val requestContentType = request.body?.contentType()?.toString()

        val fullUrl = request.url.toString()
        val matchedBaseUrl = baseUrlMatcher.match(fullUrl)
        val relativePath = fullUrl.removePrefix(matchedBaseUrl.url)

        val startTime = System.currentTimeMillis()

        var response: Response? = null

        try {
            response = chain.proceed(request)
        } catch (e: IOException) {
            val durationMs = System.currentTimeMillis() - startTime
            val apiLog = ApiLog(
                meta = ApiLogMeta(
                    method = ApiMethod.fromString(request.method),
                    baseUrlType = matchedBaseUrl.type,
                    baseUrl = matchedBaseUrl.url,
                    host = host,
                    scheme = scheme,
                    url = relativePath,
                    protocol = "unknown",
                    errorMessage = e.message,
                    startTime = startTime,
                    durationMs = durationMs,
                ),
                request = ApiLogRequest(
                    headers = headersMap,
                    headersSize = requestHeadersSize,
                    body = requestBody,
                    bodySize = requestBodySize,
                    contentType = requestContentType,
                ),
                response = ApiLogResponse(
                    code = 0,
                    message = "",
                    headers = emptyMap(),
                    headersSize = -1L,
                    body = "",
                    bodySize = -1L,
                    contentType = null,
                    tlsVersion = null,
                    cipherSuite = null,
                ),
            )
            ioScope.launch { apiLogRepository.insertLog(apiLog) }
            throw e
        }

        val durationMs = System.currentTimeMillis() - startTime
        val responseHeadersMap: Map<String, String> = response.headers.names().associateWith { name ->
            response.headers.values(name).joinToString(", ")
        }
        val responseHeadersSize = response.headers.byteCount()
        val responseBodyStr = response.peekBody(1024 * 1024).string()
        val responseBodySize = responseBodyStr.length.toLong()
        val responseContentType = response.body?.contentType()?.toString()
        val responseTlsVersion = response.handshake?.tlsVersion?.javaName
        val responseCipherSuite = response.handshake?.cipherSuite?.javaName

        runCatching {
            ApiLog(
                meta = ApiLogMeta(
                    method = ApiMethod.fromString(request.method),
                    baseUrlType = matchedBaseUrl.type,
                    baseUrl = matchedBaseUrl.url,
                    host = host,
                    scheme = scheme,
                    url = relativePath,
                    protocol = response.protocol.toString(),
                    errorMessage = null,
                    startTime = startTime,
                    durationMs = durationMs,
                ),
                request = ApiLogRequest(
                    headers = headersMap,
                    headersSize = requestHeadersSize,
                    body = requestBody,
                    bodySize = requestBodySize,
                    contentType = requestContentType,
                ),
                response = ApiLogResponse(
                    code = response.code,
                    message = response.message,
                    headers = responseHeadersMap,
                    headersSize = responseHeadersSize,
                    body = responseBodyStr,
                    bodySize = responseBodySize,
                    contentType = responseContentType,
                    tlsVersion = responseTlsVersion,
                    cipherSuite = responseCipherSuite,
                ),
            )
        }.onSuccess { logEntry ->
            ioScope.launch { apiLogRepository.insertLog(logEntry) }
        }.onFailure {
            Log.e("ApiLogInterceptor", "Failed to log API request/response", it)
        }

        return response
    }
}
