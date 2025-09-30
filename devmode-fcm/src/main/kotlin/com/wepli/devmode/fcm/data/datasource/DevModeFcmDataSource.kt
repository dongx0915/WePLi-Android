package com.wepli.devmode.fcm.data.datasource

import com.wepli.devmode.fcm.data.model.FcmMessageRequest
import com.wepli.devmode.fcm.data.model.FcmResponse
import retrofit2.Response

interface DevModeFcmDataSource {

    fun getFcmAccessToken(): String

    suspend fun sendMessage(
        projectId: String,
        accessToken: String,
        request: FcmMessageRequest,
    ): FcmResponse
}