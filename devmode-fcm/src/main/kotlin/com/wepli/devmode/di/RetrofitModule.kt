package com.wepli.devmode.di

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.wepli.devmode.di.qualifier.FcmOkHttpClient
import com.wepli.devmode.di.qualifier.FcmRetrofit
import com.wepli.domain.devmode.apilog.repository.DebugApiLogRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private val json: Json by lazy {
        Json {
            ignoreUnknownKeys = true // 알 수 없는 키 무시
            prettyPrint = true // 예쁘게 출력 (옵션)
            encodeDefaults = true // 기본 값이 할당된 경우도 직렬화
        }
    }

    @Provides
    @Singleton
    @FcmOkHttpClient
    fun provideHttpClient(
        logger: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(logger)
            .build()
    }

    @Provides
    @Singleton
    @FcmRetrofit
    fun provideRetrofit(
        @FcmOkHttpClient httpClient: OkHttpClient
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        val converterFactory = json.asConverterFactory(contentType)

        return Retrofit.Builder()
            .baseUrl("https://fcm.googleapis.com/")
            .client(httpClient)
            .addConverterFactory(converterFactory)
            .build()
    }
}