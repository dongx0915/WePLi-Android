package com.wepli.app.application

import android.app.Application
import android.graphics.Color
import com.donglab.screennameviewer.publicapi.dsl.initScreenNameViewer
import com.wepli.core.common.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import repository.setting.SettingRepository
import javax.inject.Inject

@HiltAndroidApp
class WePLiApplication : Application() {

    private val applicationScope: CoroutineScope = CoroutineScope(Dispatchers.Default)

    @Inject
    lateinit var settingRepository: SettingRepository

    override fun onCreate() {
        super.onCreate()

        initScreenNameViewer()
    }

    private fun initScreenNameViewer() = applicationScope.launch(Dispatchers.Main.immediate) {
        val isEnabled = withContext(Dispatchers.IO) {
            settingRepository.isEnableScreenNameViewer()
        }

         withContext(Dispatchers.Main.immediate) {
             initScreenNameViewer(this@WePLiApplication) {
                 settings {
                     debugModeCondition = BuildConfig.DEBUG
                     enableCondition = isEnabled
                 }
                 config {
                     textStyle {
                         this.color = Color.CYAN
                     }
                 }
             }
         }
    }
}