package com.wepli.devmode.fcm.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FcmMessageRequest(
    val message: Message
) {
    @Serializable
    data class Message(
        val token: String,
        val notification: Notification? = null,
        val android: AndroidConfig? = null,
        val data: String? = null,
    )

    @Serializable
    data class Notification(
        val title: String,
        val body: String,
    )

    @Serializable
    data class AndroidConfig(
        val priority: String = "high",
    )
}