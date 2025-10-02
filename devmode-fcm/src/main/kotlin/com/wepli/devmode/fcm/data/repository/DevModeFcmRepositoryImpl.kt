package com.wepli.devmode.fcm.data.repository

import com.wepli.devmode.fcm.data.datasource.DevModeFcmDataSource
import com.wepli.devmode.fcm.data.model.FcmMessageRequest
import com.wepli.devmode.fcm.data.model.FcmResponse
import com.wepli.devmode.fcm.domain.repository.DevModeFcmRepository
import javax.inject.Inject

class DevModeFcmRepositoryImpl @Inject constructor(
    private val devModeFcmDataSource: DevModeFcmDataSource
) : DevModeFcmRepository {

    override suspend fun saveFirebaseAdminJson(jsonContent: String) {
        devModeFcmDataSource.saveFirebaseAdminJson(jsonContent)
    }

    override suspend fun getFirebaseAdminJson(): String {
        return devModeFcmDataSource.getFirebaseAdminJson()
    }

    override suspend fun getFcmAccessToken(): String {
        return devModeFcmDataSource.getFcmAccessToken()
    }

    override suspend fun getFcmPushToken(): String {
        return devModeFcmDataSource.getFcmPushToken()
    }

    override suspend fun sendMessage(
        projectId: String,
        accessToken: String,
        request: FcmMessageRequest
    ) {
        devModeFcmDataSource.sendMessage(projectId, accessToken, request)
    }
}