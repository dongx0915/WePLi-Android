package com.wepli.data.di

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.wepli.core.common.BuildConfig
import com.wepli.data.di.qualifier.AppleMusicOkHttpClient
import com.wepli.data.di.qualifier.AppleMusicRetrofit
import com.wepli.data.di.qualifier.BaseOkHttpClient
import com.wepli.data.di.qualifier.BaseRetrofit
import com.wepli.data.di.qualifier.YoutubeRetrofit
import com.wepli.data.network.baseurl.BaseUrl
import com.wepli.data.network.calladapter.FlowCallAdapterFactory
import com.wepli.data.network.interceptor.DebugApiLogInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import debug.repository.DebugApiLogRepository
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
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

    private val prettyApiLogger = HttpLoggingInterceptor.Logger { message: String ->
        val logName = "Wepli Retrofit"

        if (message.startsWith("{") || message.startsWith("[")) {
            try {
                val jsonElement = json.parseToJsonElement(message)
                val prettyPrintJson = json.encodeToString(jsonElement)
                Log.v(logName, prettyPrintJson)
            } catch (e: Exception) {
                Log.e(logName, message)
            }
        } else {
            Log.v(logName, message)
        }
    }

    @Provides
    @Singleton
    fun provideHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(prettyApiLogger).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideDebugApiLogInterceptor(apiLogRepository: DebugApiLogRepository): Interceptor {
        return DebugApiLogInterceptor(apiLogRepository)
    }

    @Provides
    @Singleton
    @BaseOkHttpClient
    fun provideHttpClient(
        logger: HttpLoggingInterceptor,
        debugApiLogInterceptor: DebugApiLogInterceptor
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(logger)
            .addInterceptor(debugApiLogInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @AppleMusicOkHttpClient
    fun provideAppleApiHttpClient(
        logger: HttpLoggingInterceptor,
        debugApiLogInterceptor: DebugApiLogInterceptor,
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(logger)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", BuildConfig.APPLE_MUSIC_API_TOKEN)
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(debugApiLogInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @BaseRetrofit
    fun provideRetrofit(
        @BaseOkHttpClient httpClient: OkHttpClient
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        val converterFactory = json.asConverterFactory(contentType)

        return Retrofit.Builder()
            .baseUrl(BaseUrl.POSTMAN.url)
            .client(httpClient)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    @AppleMusicRetrofit
    fun provideAppleMusicRetrofit(
        @AppleMusicOkHttpClient httpClient: OkHttpClient
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        val converterFactory = json.asConverterFactory(contentType)

        return Retrofit.Builder()
            .baseUrl(BaseUrl.APPLE_MUSIC.url)
            .client(httpClient)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @YoutubeRetrofit
    fun provideYoutubeRetrofit(
        @BaseOkHttpClient httpClient: OkHttpClient
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        val converterFactory = json.asConverterFactory(contentType)

        return Retrofit.Builder()
            .baseUrl(BaseUrl.YOUTUBE.url)
            .client(httpClient)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .build()
    }
}