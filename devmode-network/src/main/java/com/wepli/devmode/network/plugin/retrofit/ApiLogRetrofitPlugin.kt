package com.wepli.devmode.network.plugin.retrofit

import android.util.Log
import com.wepli.devmode.network.data.model.BaseUrlMatcher
import com.wepli.devmode.network.data.model.ApiLog
import com.wepli.devmode.network.data.model.ApiMethod
import com.wepli.devmode.network.data.repository.DebugApiLogRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiLogRetrofitPlugin @Inject constructor(
    private val apiLogRepository: DebugApiLogRepository,
    private val baseUrlMatcher: BaseUrlMatcher
) : Interceptor {

    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun intercept(chain: Interceptor.Chain): Response {
        val startTime = System.currentTimeMillis()
        val request = chain.request()
        val response = chain.proceed(request)
        val headersMap: Map<String, String> = request.headers.names().associateWith { name ->
            request.headers.values(name).joinToString(", ")
        }

        val fullUrl = request.url.toString()
        val matchedBaseUrl = baseUrlMatcher.match(fullUrl)
        val relativePath = fullUrl.removePrefix(matchedBaseUrl.url)

        runCatching {
            ApiLog(
                method = ApiMethod.Companion.fromString(request.method),
                baseUrlType = matchedBaseUrl.type,
                baseUrl = matchedBaseUrl.url,
                url = relativePath,
                requestHeaders = headersMap,
                requestBody = request.body?.toString() ?: "Empty Request",
                responseCode = response.code,
                responseBody = response.peekBody(1024 * 1024).string(),
                startTime = startTime,
            )
        }.onSuccess { logEntry ->
            ioScope.launch { apiLogRepository.insertLog(logEntry) }
        }.onFailure {
            Log.e("ApiLogInterceptor", "Failed to log API request/response", it)
        }

        return response
    }
}