package com.wepli.devmode.fcm.datasource

import android.content.Context
import com.google.auth.oauth2.GoogleCredentials
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DevModeFcmDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
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
}