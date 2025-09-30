package com.wepli.data.devmode.fcm.repository

import com.wepli.data.devmode.fcm.datasource.DevModeFcmDataSource
import com.wepli.domain.devmode.fcm.repository.DevModeFcmRepository
import javax.inject.Inject

class DevModeFcmRepositoryImpl @Inject constructor(
    private val devModeFcmDataSource: DevModeFcmDataSource
) : DevModeFcmRepository {

    override fun getFcmAccessToken(): String {
        return devModeFcmDataSource.getFcmAccessToken()
    }
}