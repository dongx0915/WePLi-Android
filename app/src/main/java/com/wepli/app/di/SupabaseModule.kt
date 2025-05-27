package com.wepli.app.di

import android.util.Log
import com.wepli.core.common.BuildConfig
import com.wepli.data.network.interceptor.DebugUrlType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import debug.model.ApiLog
import debug.model.ApiMethod
import debug.repository.DebugApiLogRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseInternal
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {

    @OptIn(SupabaseInternal::class)
    @Provides
    @Singleton
    fun provideSupabaseClient(
        apiLogRepository: DebugApiLogRepository,
    ): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_URL,
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

                    // Header까지 기록하려면 아래 코드를 사용
                    HttpResponseValidator {
                        validateResponse { response ->
                            val startTime = System.currentTimeMillis()
                            val request = response.call.request
                            val fullUrl = request.url.toString()
                            val matchedBaseUrl = DebugUrlType.entries.firstOrNull {
                                fullUrl.startsWith(it.url)
                            } ?: DebugUrlType.UNKNOWN
                            val relativePath = fullUrl.removePrefix(matchedBaseUrl.url)

                            val responseBody = response.bodyAsText()

                            val log = ApiLog(
                                method = ApiMethod.fromString(request.method.value),
                                baseUrlType = matchedBaseUrl.value,
                                baseUrl = matchedBaseUrl.url,
                                url = relativePath,
                                requestHeaders = request.headers.toString(),
                                requestBody = "", // <- 이 부분은 Ktor에서 직접 얻기 어려움
                                responseCode = response.status.value,
                                responseBody = responseBody,
                                durationMs = System.currentTimeMillis() - startTime // <- 필요하면 시간 측정 로직 추가
                            )

                            apiLogRepository.addLog(log)
                            Log.d("Supabase Log", log.toString())
                        }
                    }
                }
            }
        }
    }
}