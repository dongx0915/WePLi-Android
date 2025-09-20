package com.wepli.app.application

import android.app.Application
import android.graphics.Color
import com.donglab.screennameviewer.publicapi.dsl.initScreenNameViewer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WePLiApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initScreenNameViewer()
    }

    private fun initScreenNameViewer() {
        initScreenNameViewer(this) {
            settings {
                debugMode { true }
                enabled { true }
            }
            config {
                textStyle {
                    this.color = Color.CYAN
                }
            }
        }
    }
}