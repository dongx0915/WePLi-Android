package com.wepli.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.wepli.data.di.qualifier.AppleMusicOkHttpClient
import com.wepli.data.di.qualifier.AppleMusicRetrofit
import com.wepli.data.di.qualifier.BaseOkHttpClient
import com.wepli.data.di.qualifier.BaseRetrofit
import com.wepli.data.network.calladapter.FlowCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val BASE_URL = "https://dfce38cf-c9ca-4eaa-867f-8c9240c4dc53.mock.pstmn.io/"
    private const val APPLE_MUSIC_BASE_URL = "https://api.music.apple.com/"

    private val json: Json by lazy {
        Json {
            ignoreUnknownKeys = true // 알 수 없는 키 무시
            prettyPrint = true // 예쁘게 출력 (옵션)
            encodeDefaults = true // 기본 값이 할당된 경우도 직렬화
        }
    }

    @Provides
    @Singleton
    fun provideHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    @BaseOkHttpClient
    fun provideHttpClient(
        logger: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(logger)
            .build()
    }

    @Provides
    @Singleton
    @AppleMusicOkHttpClient
    fun provideAppleApiHttpClient(
        logger: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(logger)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer eyJraWQiOiI3QlU4R1pBNDREIiwiYWxnIjoiRVMyNTYifQ.eyJpc3MiOiI4UTRIN1gzUTU4IiwiZXhwIjoxNzQ0MzcxOTY2LCJpYXQiOjE3MzU3MzE5NjZ9.K3j7cGJvs1A6Xt_boYCjrMVrbjRRRVG0MYeStT19hHByMCX0iK2cFBlkmglWEPo4g2-Gt_kxRtnE-npCuxHEIg")
                    .build()
                chain.proceed(request)
            }
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
            .baseUrl(BASE_URL)
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
            .baseUrl(APPLE_MUSIC_BASE_URL)
            .client(httpClient)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .build()
    }
}