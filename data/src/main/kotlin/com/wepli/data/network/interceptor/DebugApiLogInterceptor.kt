package com.wepli.data.network.interceptor

import android.util.Log
import debug.model.ApiLog
import debug.model.ApiMethod
import debug.repository.DebugApiLogRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class DebugApiLogInterceptor @Inject constructor(
    private val apiLogRepository: DebugApiLogRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val startTime = System.currentTimeMillis()
        val request = chain.request()
        val response = chain.proceed(request)

        runCatching {
            ApiLog(
                method = ApiMethod.fromString(request.method),
                url = request.url.toString(),
                requestHeaders = request.headers.toString(),
                requestBody = request.body?.toString() ?: "Empty Request",
                responseCode = response.code,
                responseBody = response.peekBody(1024 * 1024).string(),
                durationMs = System.currentTimeMillis() - startTime
            )
        }.onSuccess { logEntry ->
            apiLogRepository.addLog(logEntry)
        }.onFailure {
            Log.e("ApiLogInterceptor", "Failed to log API request/response", it)
        }

        return response
    }
}