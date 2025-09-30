package com.wepli.data.devmode.fcm.datasource

import com.google.auth.oauth2.GoogleCredentials
import java.io.FileInputStream
import javax.inject.Inject

class DevModeFcmDataSourceImpl @Inject constructor() : DevModeFcmDataSource {

    override fun getFcmAccessToken(): String {
        return runCatching {
            val googleCredentials = GoogleCredentials
                .fromStream(FileInputStream("app/wepli-app-49e90-firebase-adminsdk-iiac3-ffdc129e6f.json"))
                .createScoped("https://www.googleapis.com/auth/firebase.messaging")
                .also {
                    it?.refreshIfExpired()
                }

            googleCredentials?.accessToken?.tokenValue.orEmpty()
        }.getOrDefault("Unknown")
    }
}