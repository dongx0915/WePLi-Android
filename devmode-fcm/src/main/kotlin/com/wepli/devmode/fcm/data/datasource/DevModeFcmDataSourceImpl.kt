package com.wepli.devmode.fcm.data.datasource

import android.content.Context
import com.google.auth.oauth2.GoogleCredentials
import com.wepli.devmode.fcm.data.api.FcmApi
import com.wepli.devmode.fcm.data.model.FcmMessageRequest
import com.wepli.devmode.fcm.data.model.FcmResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DevModeFcmDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fcmApi: FcmApi
) : DevModeFcmDataSource {

    override fun getFcmAccessToken(): String {
        return runCatching {
            val googleCredentials = GoogleCredentials
                .fromStream(context.assets.open("wepli-app-49e90-firebase-adminsdk-iiac3-ffdc129e6f.json"))
                .createScoped("https://www.googleapis.com/auth/firebase.messaging")
                .also {
                    it?.refreshIfExpired()
                }

            googleCredentials?.accessToken?.tokenValue.orEmpty()
        }.getOrElse {
            it.message.toString()
        }
    }

    override suspend fun sendMessage(
        projectId: String,
        accessToken: String,
        request: FcmMessageRequest
    ): FcmResponse {
        val response = fcmApi.sendMessage(
            projectId = projectId,
            authorization = "Bearer $accessToken",
            request = request,
        )

        return if (response.isSuccessful && response.body() != null) {
            response.body()!!
        } else {
            throw Exception(response.errorBody()?.string())
        }
    }
}