package com.wepli.devmode.fcm.data.repository

import com.wepli.devmode.fcm.data.datasource.DevModeFcmDataSource
import com.wepli.devmode.fcm.domain.repository.DevModeFcmRepository
import javax.inject.Inject

class DevModeFcmRepositoryImpl @Inject constructor(
    private val devModeFcmDataSource: DevModeFcmDataSource
) : DevModeFcmRepository {

    override fun getFcmAccessToken(): String {
        return devModeFcmDataSource.getFcmAccessToken()
    }
}