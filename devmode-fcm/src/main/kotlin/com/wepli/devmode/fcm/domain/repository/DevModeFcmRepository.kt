package com.wepli.devmode.fcm.domain.repository

import com.wepli.devmode.fcm.data.model.FcmMessageRequest

interface DevModeFcmRepository {

    suspend fun saveFirebaseAdminJson(jsonContent: String)

    suspend fun getFirebaseAdminJson(): String

    suspend fun getFcmAccessToken(): String

    suspend fun sendMessage(
        projectId: String,
        accessToken: String,
        request: FcmMessageRequest,
    )
}