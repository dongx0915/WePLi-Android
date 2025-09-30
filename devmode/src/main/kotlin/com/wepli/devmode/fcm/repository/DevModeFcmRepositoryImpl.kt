package com.wepli.devmode.fcm.repository

import com.wepli.devmode.fcm.datasource.DevModeFcmDataSource
import javax.inject.Inject

class DevModeFcmRepositoryImpl @Inject constructor(
    private val devModeFcmDataSource: DevModeFcmDataSource
) : DevModeFcmRepository {

    override fun getFcmAccessToken(): String {
        return devModeFcmDataSource.getFcmAccessToken()
    }
}