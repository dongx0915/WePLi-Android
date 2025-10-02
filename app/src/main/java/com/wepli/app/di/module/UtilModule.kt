package com.wepli.app.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import util.notification.NotificationManager

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Provides
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager {
        return NotificationManager(context)
    }
}