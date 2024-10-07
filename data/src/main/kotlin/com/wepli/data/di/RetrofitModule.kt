package com.wepli.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val BASE_URL = "https://4ba56462-20c9-44a7-801e-fc3d459e4dc5.mock.pstmn.io/"

    @Provides
    @Singleton
    fun provideHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        logger: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(logger)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true // 알 수 없는 키 무시
            prettyPrint = true // 예쁘게 출력 (옵션)
            encodeDefaults = true // 기본 값이 할당된 경우도 직렬화
        }

        val contentType = "application/json".toMediaType()
        val converterFactory = json.asConverterFactory(contentType)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .build()
    }
}