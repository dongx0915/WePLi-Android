package com.wepli.data.di

import com.wepli.data.chart.ChartApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideChartService(retrofit: Retrofit): ChartApi = retrofit.create(ChartApi::class.java)
}