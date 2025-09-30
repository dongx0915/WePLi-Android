package com.wepli.devmode.fcm.data.model

import com.wepli.devmode.fcm.domain.model.FcmMessage
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
        val data: Map<String, String>? = null,
    )

    @Serializable
    data class Notification(
        val title: String? = null,
        val body: String? = null,
    )

    @Serializable
    data class AndroidConfig(
        val priority: String? = "high",
    )
}

fun FcmMessage.toFcmMessageRequest(): FcmMessageRequest {
    return FcmMessageRequest(
        message = FcmMessageRequest.Message(
            token = message.token,
            notification = FcmMessageRequest.Notification(
                title = message.notification?.title,
                body = message.notification?.body,
            ),
            android = FcmMessageRequest.AndroidConfig(
                priority = message.android?.priority
            ),
            data = message.data
        )
    )
}