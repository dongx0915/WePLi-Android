package com.wepli.devmode.fcm.data.api

import com.wepli.devmode.fcm.data.model.FcmMessageRequest
import com.wepli.devmode.fcm.data.model.FcmResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface FcmApi {

    @POST("v1/projects/{projectId}/messages:send")
    suspend fun sendMessage(
        @Path("projectId") projectId: String,
        @Header("Authorization") authorization: String,
        @Header("Content-Type") contentType: String = "application/json",
        @Body request: FcmMessageRequest,
    ): Response<FcmResponse>
}