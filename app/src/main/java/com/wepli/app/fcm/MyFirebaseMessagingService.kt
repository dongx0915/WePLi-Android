package com.wepli.app.fcm

import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import extensions.toJsonString
import util.notification.NotificationManager
import javax.inject.Inject

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var mainIntent: Intent

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