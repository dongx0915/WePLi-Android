package com.wepli.app.fcm

import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.wepli.app.di.entrypoint.UtilEntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.android.AndroidEntryPoint
import extensions.toJsonString
import util.notification.NotificationManager

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {

    val notificationManager: NotificationManager by lazy {
        EntryPoints
            .get(this.applicationContext, UtilEntryPoint::class.java)
            .getNotificationManager()
    }

    val mainIntent: Intent by lazy {
        EntryPoints
            .get(this.applicationContext, UtilEntryPoint::class.java)
            .getMainIntent()
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        notificationManager.showNotification(
            title = message.notification?.title ?: message.data["title"].orEmpty(),
            message = message.notification?.body ?: message.data.toJsonString(),
            intent = mainIntent
        )
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}