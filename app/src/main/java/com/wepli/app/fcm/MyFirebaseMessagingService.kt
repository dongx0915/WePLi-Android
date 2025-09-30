package com.wepli.app.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import extensions.toJsonString
import extensions.toPrettyJsonString

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("메시지", "수신: ${message.toPrettyJsonString()}")
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}