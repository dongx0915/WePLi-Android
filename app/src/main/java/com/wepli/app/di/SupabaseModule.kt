package com.wepli.app.di

import android.util.Log
import com.wepli.core.common.BuildConfig
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
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {

    @OptIn(SupabaseInternal::class)
    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
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
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            Log.v("Supabase Log", message)
                        }
                    }
                    level = LogLevel.BODY
                }
            }
        }
    }
}