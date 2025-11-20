package com.wepli.devmode.network.data.ktor

import com.wepli.devmode.network.data.interceptor.BaseUrlMatcher
import com.wepli.devmode.network.data.model.ApiLog
import com.wepli.devmode.network.data.model.ApiMethod
import com.wepli.devmode.network.data.repository.DebugApiLogRepository
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.HttpClientCall
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.api.Send
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.content.ByteArrayContent
import io.ktor.http.content.OutgoingContent
import io.ktor.http.content.TextContent
import io.ktor.util.AttributeKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiLogKtorPlugin @Inject constructor(
    private val apiLogRepository: DebugApiLogRepository,
    private val baseUrlMatcher: BaseUrlMatcher
) {
    private val RequestBodyKey = AttributeKey<String>("requestBodyForLog")

    private val requestBodyCapture = createClientPlugin("RequestBodyCapture") {
        on(Send) { request ->
            val bodyStr = when (val content = request.body) {
                is TextContent -> content.text
                is ByteArrayContent -> runCatching { content.bytes().decodeToString() }.getOrDefault("<byte-array>")
                is FormDataContent -> content.formData.toString()
                is MultiPartFormDataContent -> "<multipart>"
                is OutgoingContent.NoContent -> ""
                else -> content.toString()
            }
            val call: HttpClientCall = proceed(request)
            call.attributes.put(RequestBodyKey, bodyStr)
            call
        }
    }

    fun install(config: HttpClientConfig<*>) {
        config.install(requestBodyCapture)
        config.HttpResponseValidator {
            validateResponse { response ->
                val apiLog = response.toApiLog()
                apiLogRepository.insertLog(apiLog)
            }
        }
    }

    private suspend fun HttpResponse.toApiLog(): ApiLog {
        val request = call.request
        val requestHeaders = request.headers.entries()
            .associate { (key, values) -> key to values.joinToString(", ") }
        val requestBody = call.attributes.getOrNull(RequestBodyKey).orEmpty()

        val fullUrl = request.url.toString()
        val matched = baseUrlMatcher.match(fullUrl)
        val relativePath = fullUrl.removePrefix(matched.url)

        return ApiLog(
            method = ApiMethod.fromString(request.method.value),
            baseUrlType = matched.type,
            baseUrl = matched.url,
            url = relativePath,
            requestHeaders = requestHeaders,
            requestBody = requestBody,
            responseCode = status.value,
            responseBody = bodyAsText(),
            startTime = requestTime.timestamp,
        )
    }
}
