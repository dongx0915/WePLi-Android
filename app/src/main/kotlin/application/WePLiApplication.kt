package application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WePLiApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}