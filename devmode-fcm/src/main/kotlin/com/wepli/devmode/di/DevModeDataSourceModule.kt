package com.wepli.devmode.di

import com.wepli.devmode.fcm.datasource.DevModeFcmDataSource
import com.wepli.devmode.fcm.datasource.DevModeFcmDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DevModeDataSourceModule {

    @Binds
    @Singleton
    fun bindDevFcmDataSource(dataSource: DevModeFcmDataSourceImpl): DevModeFcmDataSource
}