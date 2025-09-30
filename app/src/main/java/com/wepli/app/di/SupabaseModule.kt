package com.wepli.app.di

import android.util.Log
import com.wepli.core.common.BuildConfig
import com.wepli.data.network.baseurl.BaseUrl
import com.wepli.domain.devmode.apilog.model.ApiLog
import com.wepli.domain.devmode.apilog.model.ApiMethod
import com.wepli.domain.devmode.apilog.repository.DebugApiLogRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseInternal
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import io.github.jan.supabase.storage.Storage
import io.ktor.client.call.HttpClientCall
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.api.Send
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.content.ByteArrayContent
import io.ktor.http.content.OutgoingContent
import io.ktor.http.content.TextContent
import io.ktor.util.AttributeKey
import kotlinx.serialization.json.Json
import javax.inject.Singleton
import kotlin.collections.component1
import kotlin.collections.component2

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {

    private val RequestBodyKey = AttributeKey<String>("requestBodyForLog")

    private val RequestBodyCapture = createClientPlugin("RequestBodyCapture") {
        on(Send) { request ->
            val bodyStr = when (val content = request.body) {
                is TextContent -> content.text
                is ByteArrayContent -> runCatching { content.bytes().decodeToString() }.getOrDefault("<byte-array>")
                is FormDataContent -> content.formData.toString()           // 간단 표기
                is MultiPartFormDataContent -> "<multipart>"
                is OutgoingContent.NoContent -> ""
                else -> content.toString()
            }

            val call: HttpClientCall = proceed(request)
            call.attributes.put(RequestBodyKey, bodyStr)
            call
        }
    }

    @OptIn(SupabaseInternal::class)
    @Provides
    @Singleton
    fun provideSupabaseClient(
        apiLogRepository: DebugApiLogRepository,
    ): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = BaseUrl.SUPABASE.url,
            supabaseKey = BuildConfig.SUPABASE_KEY
        ) {
            defaultSerializer = KotlinXSerializer(
                Json {
                    ignoreUnknownKeys = true // 알 수 없는 키 무시
                    prettyPrint = true // 예쁘게 출력 (옵션)
                    encodeDefaults = true // 기본 값이 할당된 경우도 직렬화
                }
            )

            install(Auth)
            install(Postgrest)
            install(Storage)

            httpConfig {
                if (BuildConfig.DEBUG) {
                    // 일반 Logging
                    /* install(Logging) {
                        logger = object : Logger {
                            override fun log(message: String) {
                                Log.v("Supabase Log", message)
                            }
                        }
                        level = LogLevel.BODY
                    } */

                    install(RequestBodyCapture)

                    // Header까지 기록하려면 아래 코드를 사용
                    HttpResponseValidator {
                        validateResponse { response ->
                            val apiLog = response.toApiLog()
                            apiLogRepository.insertLog(apiLog)
                            Log.d("Supabase Log", apiLog.toString())
                        }
                    }
                }
            }
        }
    }

    private fun Headers.toSingleValueMap(separator: String): Map<String, String> {
        return entries().associate { (key, values) ->
            key to values.joinToString(separator)
        }
    }

    suspend fun HttpResponse.toApiLog(): ApiLog {
        val request = call.request
        val requestHeaders: Map<String, String> = request.headers.toSingleValueMap(", ")
        val requestBodyForLog = call.attributes.getOrNull(RequestBodyKey).orEmpty()

        val fullUrl = request.url.toString()
        val matchedBaseUrl = BaseUrl.entries.firstOrNull { fullUrl.startsWith(it.url) } ?: BaseUrl.UNKNOWN
        val relativePath = fullUrl.removePrefix(matchedBaseUrl.url)

        return ApiLog(
            method = ApiMethod.fromString(request.method.value),
            baseUrlType = matchedBaseUrl.value,
            baseUrl = matchedBaseUrl.url,
            url = relativePath,
            requestHeaders = requestHeaders,
            requestBody = requestBodyForLog,
            responseCode = status.value,
            responseBody = bodyAsText(),
            startTime = requestTime.timestamp,
        )
    }
}