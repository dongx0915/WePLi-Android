package com.wepli.devmode.fcm.data.model

data class FcmMessageRequest(
    val message: Message
) {
    data class Message(
        val token: String,
        val notification: Notification,
        val android: AndroidConfig? = null
    )

    data class Notification(
        val title: String,
        val body: String,
    )

    data class AndroidConfig(
        val priority: String = "high",
    )
}