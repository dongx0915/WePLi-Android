package com.wepli.devmode.network.plugin.ktor

import com.wepli.devmode.network.data.model.ApiLog
import com.wepli.devmode.network.data.model.ApiLogMeta
import com.wepli.devmode.network.data.model.ApiLogRequest
import com.wepli.devmode.network.data.model.ApiLogResponse
import com.wepli.devmode.network.data.model.ApiMethod
import com.wepli.devmode.network.data.model.BaseUrlInfo
import com.wepli.devmode.network.data.model.BaseUrlMatcher
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
import io.ktor.http.contentLength
import io.ktor.util.AttributeKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiLogKtorPlugin @Inject constructor(
    private val apiLogRepository: DebugApiLogRepository,
    private val baseUrlMatcher: BaseUrlMatcher
) {
    private val requestBodyKey = AttributeKey<String>("requestBodyForLog")

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
            call.attributes.put(requestBodyKey, bodyStr)
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
        val fullUrl = call.request.url.toString()
        val matched = baseUrlMatcher.match(fullUrl)
        val relativePath = fullUrl.removePrefix(matched.url)
        val startTime = requestTime.timestamp
        val durationMs = System.currentTimeMillis() - startTime

        val requestBody = call.attributes.getOrNull(requestBodyKey).orEmpty()
        val responseBody = bodyAsText()

        return ApiLog(
            meta = call.buildMeta(matched, relativePath, startTime, durationMs, version.toString()),
            request = call.buildRequest(requestBody),
            response = buildResponse(responseBody),
        )
    }

    private fun HttpClientCall.buildMeta(
        baseUrlInfo: BaseUrlInfo,
        relativePath: String,
        startTime: Long,
        durationMs: Long,
        protocol: String,
    ): ApiLogMeta = ApiLogMeta(
        method = ApiMethod.fromString(request.method.value),
        baseUrlType = baseUrlInfo.type,
        baseUrl = baseUrlInfo.url,
        host = request.url.host,
        scheme = request.url.protocol.name,
        url = relativePath,
        protocol = protocol,
        errorMessage = null,
        startTime = startTime,
        durationMs = durationMs,
    )

    private fun HttpClientCall.buildRequest(body: String): ApiLogRequest {
        val parsedHeaders = request.headers
            .entries()
            .associate { (key, values) ->
                key to values.joinToString(", ")
            }

        return ApiLogRequest(
            headers = parsedHeaders,
            headersSize = parsedHeaders.entries.sumOf { (k, v) -> k.length + v.length + 4L },
            body = body,
            bodySize = request.headers["Content-Length"]?.toLongOrNull() ?: -1L,
            contentType = request.headers["Content-Type"],
        )
    }

    private fun HttpResponse.buildResponse(body: String): ApiLogResponse {
        val parsedHeaders = headers
            .entries()
            .associate { (key, values) ->
                key to values.joinToString(", ")
            }

        return ApiLogResponse(
            code = status.value,
            message = status.description,
            headers = parsedHeaders,
            headersSize = parsedHeaders.entries.sumOf { (k, v) -> k.length + v.length + 4L },
            body = body,
            bodySize = contentLength() ?: body.length.toLong(),
            contentType = headers["Content-Type"],
            tlsVersion = null,
            cipherSuite = null,
        )
    }
}
