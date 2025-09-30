package com.wepli.devmode.fcm.domain.model

data class FcmMessage(
    val message: Message
) {

    data class Message(
        val token: String,
        val notification: Notification? = null,
        val android: AndroidConfig? = null,
        val data: Map<String, String>? = null,
    )

    data class Notification(
        val title: String,
        val body: String,
    )

    data class AndroidConfig(
        val priority: String = "high",
    )
}