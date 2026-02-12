package com.wepli.devmode.network.di

import android.content.Context
import com.wepli.devmode.network.data.dao.ApiLogDao
import com.wepli.devmode.network.data.datasource.DebugApiLogLocalDatasource
import com.wepli.devmode.network.data.datasource.DebugApiLogLocalDatasourceImpl
import com.wepli.devmode.network.data.db.ApiLogDatabase
import com.wepli.devmode.network.data.model.BaseUrlMatcher
import com.wepli.devmode.network.plugin.retrofit.ApiLogRetrofitInterceptor
import com.wepli.devmode.network.data.repository.DebugApiLogRepository
import com.wepli.devmode.network.data.repository.DebugApiLogRepositoryImpl
import com.wepli.devmode.network.plugin.ktor.ApiLogKtorPlugin
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkLogModule {

    @Binds
    @Singleton
    fun bindDebugApiLogRepository(debugApiLogRepositoryImpl: DebugApiLogRepositoryImpl): DebugApiLogRepository

    @Binds
    @Singleton
    fun bindDebugApiLogLocalDatasource(debugApiLogLocalDatasourceImpl: DebugApiLogLocalDatasourceImpl): DebugApiLogLocalDatasource

    companion object {
        @Provides
        @Singleton
        fun provideApiLogDatabase(
            @ApplicationContext context: Context
        ): ApiLogDatabase {
            return ApiLogDatabase.create(context)
        }

        @Provides
        fun provideApiLogDao(database: ApiLogDatabase): ApiLogDao {
            return database.apiLogDao()
        }

        @Provides
        @IntoSet
        @Singleton
        fun provideApiLogRetrofitInterceptor(
            apiLogRepository: DebugApiLogRepository,
            baseUrlMatcher: BaseUrlMatcher
        ): Interceptor {
            return ApiLogRetrofitInterceptor(apiLogRepository, baseUrlMatcher)
        }

        @Provides
        @Singleton
        fun provideApiLogKtorPlugin(
            apiLogRepository: DebugApiLogRepository,
            baseUrlMatcher: BaseUrlMatcher
        ): ApiLogKtorPlugin {
            return ApiLogKtorPlugin(apiLogRepository, baseUrlMatcher)
        }
    }
}
