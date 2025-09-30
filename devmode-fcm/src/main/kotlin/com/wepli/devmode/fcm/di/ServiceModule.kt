package com.wepli.devmode.fcm.di

import com.wepli.devmode.fcm.di.qualifier.FcmRetrofit
import com.wepli.devmode.fcm.data.api.FcmApi
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
    fun provideFcmService(@FcmRetrofit retrofit: Retrofit): FcmApi = retrofit.create(FcmApi::class.java)
}