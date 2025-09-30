package com.wepli.devmode.fcm.domain.repository

import com.wepli.devmode.fcm.data.model.FcmMessageRequest

interface DevModeFcmRepository {

    fun getFcmAccessToken(): String

    suspend fun sendMessage(
        projectId: String,
        accessToken: String,
        request: FcmMessageRequest,
    )
}