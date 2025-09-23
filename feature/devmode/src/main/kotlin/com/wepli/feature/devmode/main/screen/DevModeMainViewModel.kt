package com.wepli.feature.devmode.main.screen

import base.BaseMviViewModel
import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.feature.devmode.main.utils.DevModeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import repository.user.UserRepository
import javax.inject.Inject

data class DevModeMainState(
    val androidOs: String = "",
    val sdkVersion: Int = 0,
    val deviceModel: String = "",
    val resourceBucket: String = "",
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
    override fun processIntent(intent: DevModeMainIntent) {
        when (intent) {
            is DevModeMainIntent.Init -> updateState {
                copy(
                    androidOs = intent.androidOs,
                    sdkVersion = intent.sdkVersion,
                    deviceModel = intent.deviceModel,
                    resourceBucket = intent.resourceBucket,
                    deviceWidth = intent.deviceWidth,
                    deviceHeight = intent.deviceHeight,
                )
            }
        }
    }
}