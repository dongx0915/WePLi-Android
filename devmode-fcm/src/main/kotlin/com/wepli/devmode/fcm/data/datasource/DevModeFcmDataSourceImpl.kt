package com.wepli.devmode.fcm.data.datasource

import com.google.auth.oauth2.GoogleCredentials
import com.wepli.devmode.fcm.data.api.FcmApi
import com.wepli.devmode.fcm.data.datastore.DataStoreKey
import com.wepli.devmode.fcm.data.datastore.local.DataStorePrefDataSource
import com.wepli.devmode.fcm.data.model.FcmMessageRequest
import com.wepli.devmode.fcm.data.model.FcmResponse
import java.io.ByteArrayInputStream
import javax.inject.Inject

class DevModeFcmDataSourceImpl @Inject constructor(
    private val fcmApi: FcmApi,
    private val dataStorePrefDataSource: DataStorePrefDataSource
) : DevModeFcmDataSource {

    override suspend fun saveFirebaseAdminJson(jsonContent: String) {
        dataStorePrefDataSource.setString(
            key = DataStoreKey.FIREBASE_API_KEY_JSON,
            value = jsonContent
        )
    }

    override suspend fun getFirebaseAdminJson(): String? {
        val json = dataStorePrefDataSource.getString(
            key = DataStoreKey.FIREBASE_API_KEY_JSON,
            defaultValue = ""
        )
        return json.ifEmpty { null }
    }

    override suspend fun getFcmAccessToken(): String {
        return runCatching {
            val jsonContent = getFirebaseAdminJson() ?: throw Exception("Firebase Admin JSON not found")

            val inputStream = ByteArrayInputStream(jsonContent.toByteArray(Charsets.UTF_8))

            val googleCredentials = GoogleCredentials
                .fromStream(inputStream)
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