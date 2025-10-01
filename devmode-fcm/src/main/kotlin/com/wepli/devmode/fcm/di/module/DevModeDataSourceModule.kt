package com.wepli.devmode.fcm.di.module

import com.wepli.devmode.fcm.data.datasource.DevModeFcmDataSource
import com.wepli.devmode.fcm.data.datasource.DevModeFcmDataSourceImpl
import com.wepli.devmode.fcm.data.datastore.local.DataStorePrefDataSource
import com.wepli.devmode.fcm.data.datastore.local.DataStorePrefDataSourceImpl
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

    @Binds
    @Singleton
    fun bindDataStoreDataSource(dataStoreDataSourceImpl: DataStorePrefDataSourceImpl): DataStorePrefDataSource
}