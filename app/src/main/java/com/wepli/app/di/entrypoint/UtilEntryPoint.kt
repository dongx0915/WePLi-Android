package com.wepli.app.di.entrypoint

import android.content.Intent
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import util.notification.NotificationManager

@EntryPoint
@InstallIn(SingletonComponent::class)
interface UtilEntryPoint {

    fun getNotificationManager(): NotificationManager

    fun getMainIntent(): Intent
}