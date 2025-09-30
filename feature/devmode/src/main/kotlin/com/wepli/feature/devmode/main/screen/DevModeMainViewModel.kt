package com.wepli.feature.devmode.main.screen

import base.BaseMviViewModel
import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.devmode.fcm.repository.DevModeFcmRepository
import com.wepli.feature.devmode.main.utils.DevModeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import repository.setting.SettingRepository
import repository.user.UserRepository
import javax.inject.Inject

data class DevModeMainState(
    val accessToken: String = "",
    val refreshToken: String = "",
    val isEnabledScreenNameViewer: Boolean = false,
    val fcmToken: String = "",
    val fcmAccessToken: String = "",
    val androidOs: String = "",
    val sdkVersion: Int = 0,
    val deviceModel: String = "",
    val resourceBucket: String = "",
    val density: Int = 0,
    val deviceWidth: Int = 0,
    val deviceHeight: Int = 0,
) : UiState

sealed interface DevModeMainEffect : SideEffect {
    data object RestartApplication : DevModeMainEffect
}

sealed interface DevModeMainIntent : Intent {
    data class Init(
        val androidOs: String,
        val sdkVersion: Int,
        val deviceModel: String,
        val resourceBucket: String,
        val density: Int,
        val deviceWidth: Int,
        val deviceHeight: Int,
    ) : DevModeMainIntent

    data class ChangeScreenNameViewerState(val enabled: Boolean) : DevModeMainIntent
}

@HiltViewModel
class DevModeMainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val settingRepository: SettingRepository,
    private val devModeFcmRepository: DevModeFcmRepository,
) : BaseMviViewModel<DevModeMainState, DevModeMainEffect, DevModeMainIntent>(
    initialState = DevModeMainState()
) {
    init {
        initState()
    }

    override fun processIntent(intent: DevModeMainIntent) {
        when (intent) {
            is DevModeMainIntent.Init -> updateState {
                copy(
                    androidOs = intent.androidOs,
                    sdkVersion = intent.sdkVersion,
                    deviceModel = intent.deviceModel,
                    resourceBucket = intent.resourceBucket,
                    density = intent.density,
                    deviceWidth = intent.deviceWidth,
                    deviceHeight = intent.deviceHeight,
                )
            }

            is DevModeMainIntent.ChangeScreenNameViewerState -> updateScreenNameViewerSetting(intent.enabled)
        }
    }

    private fun initState() = intent {
        launch(Dispatchers.IO) {
            val accessToken = userRepository.getAccessToken()
            val refreshToken = userRepository.getRefreshToken()
            val fcmToken = DevModeUtil.getFcmToken()
            val fcmAccessToken = devModeFcmRepository.getFcmAccessToken()
            val isEnabledScreenNameViewer = settingRepository.isEnableScreenNameViewer()

            reduce {
                state.copy(
                    accessToken = accessToken,
                    refreshToken = refreshToken,
                    fcmToken = fcmToken,
                    fcmAccessToken = fcmAccessToken,
                    isEnabledScreenNameViewer = isEnabledScreenNameViewer,
                )
            }
        }
    }

    private fun updateScreenNameViewerSetting(isEnabled: Boolean) = launch {
        settingRepository.setEnableScreenNameViewer(isEnabled)

        postSideEffect { DevModeMainEffect.RestartApplication }
    }
}