package com.wepli.feature.devmode.main.screen

import android.util.Log
import base.BaseMviViewModel
import base.Intent
import base.SideEffect
import base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import repository.user.UserRepository
import javax.inject.Inject

data class DevModeMainState(
    val accessToken: String = "",
    val refreshToken: String = "",
    val fcmToken: String = "",
    val androidOs: String = "",
    val sdkVersion: Int = 0,
    val deviceModel: String = "",
    val resourceBucket: String = "",
    val density: Int = 0,
    val deviceWidth: Int = 0,
    val deviceHeight: Int = 0,
) : UiState

sealed interface DevModeMainEffect : SideEffect

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
}

@HiltViewModel
class DevModeMainViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseMviViewModel<DevModeMainState, DevModeMainEffect, DevModeMainIntent>(
    initialState = DevModeMainState()
) {
    init {
        updateUserInfo()
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
        }
    }

    private fun updateUserInfo() = intent {
        launch(Dispatchers.IO) {
            val accessToken = userRepository.getAccessToken()
            val refreshToken = userRepository.getRefreshToken()

            reduce {
                state.copy(
                    accessToken = accessToken,
                    refreshToken = refreshToken,
                )
            }
        }
    }
}