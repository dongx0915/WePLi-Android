package com.wepli.devmode.fcm.di.module

import com.wepli.devmode.fcm.domain.repository.DevModeFcmRepository
import com.wepli.devmode.fcm.data.repository.DevModeFcmRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DevModeRepositoryModule {

    @Binds
    @Singleton
    fun bindDevFcmRepository(devModeFcmRepositoryImpl: DevModeFcmRepositoryImpl): DevModeFcmRepository
}