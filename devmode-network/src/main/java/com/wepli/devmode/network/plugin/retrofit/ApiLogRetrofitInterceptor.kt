package com.wepli.devmode.network.plugin.retrofit

import android.util.Log
import com.wepli.devmode.network.data.model.ApiLog
import com.wepli.devmode.network.data.model.ApiLogMeta
import com.wepli.devmode.network.data.model.ApiLogRequest
import com.wepli.devmode.network.data.model.ApiLogResponse
import com.wepli.devmode.network.data.model.ApiMethod
import com.wepli.devmode.network.data.model.BaseUrlInfo
import com.wepli.devmode.network.data.model.BaseUrlMatcher
import com.wepli.devmode.network.data.repository.DebugApiLogRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Request
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
        val apiRequest = request.buildRequest()

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
                meta = request.buildMeta(matchedBaseUrl, relativePath, startTime, durationMs, "unknown", e.message),
                request = apiRequest,
                response = ApiLogResponse.default(),
            )
            ioScope.launch { apiLogRepository.insertLog(apiLog) }
            throw e
        }

        runCatching {
            val durationMs = System.currentTimeMillis() - startTime

            ApiLog(
                meta = request.buildMeta(matchedBaseUrl, relativePath, startTime, durationMs, response.protocol.toString()),
                request = apiRequest,
                response = response.buildResponse(),
            )
        }.onSuccess { logEntry ->
            ioScope.launch { apiLogRepository.insertLog(logEntry) }
        }.onFailure {
            Log.e("ApiLogInterceptor", "Failed to log API request/response", it)
        }

        return response
    }

    private fun Request.buildMeta(
        baseUrlInfo: BaseUrlInfo,
        relativePath: String,
        startTime: Long,
        durationMs: Long,
        protocol: String,
        errorMessage: String? = null,
    ): ApiLogMeta = ApiLogMeta(
        method = ApiMethod.fromString(method),
        baseUrlType = baseUrlInfo.type,
        baseUrl = baseUrlInfo.url,
        host = url.host,
        scheme = url.scheme,
        url = relativePath,
        protocol = protocol,
        errorMessage = errorMessage,
        startTime = startTime,
        durationMs = durationMs,
    )

    private fun Request.buildRequest(): ApiLogRequest {
        val parsedHeaders: Map<String, String> = headers.names().associateWith { name ->
            headers.values(name).joinToString(", ")
        }
        val parsedBody = body?.let {
            val buffer = Buffer()
            it.writeTo(buffer)
            buffer.readUtf8()
        } ?: ""

        return ApiLogRequest(
            headers = parsedHeaders,
            headersSize = headers.byteCount(),
            body = parsedBody,
            bodySize = body?.contentLength() ?: -1L,
            contentType = body?.contentType()?.toString(),
        )
    }

    private fun Response.buildResponse(): ApiLogResponse {
        val parsedHeaders: Map<String, String> = headers.names().associateWith { name ->
            headers.values(name).joinToString(", ")
        }
        val parsedBody = peekBody(1024 * 1024).string()

        return ApiLogResponse(
            code = code,
            message = message,
            headers = parsedHeaders,
            headersSize = headers.byteCount(),
            body = parsedBody,
            bodySize = parsedBody.length.toLong(),
            contentType = body?.contentType()?.toString(),
            tlsVersion = handshake?.tlsVersion?.javaName,
            cipherSuite = handshake?.cipherSuite?.javaName,
        )
    }
}
