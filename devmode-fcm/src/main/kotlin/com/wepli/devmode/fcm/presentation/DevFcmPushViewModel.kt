package com.wepli.devmode.fcm.presentation

import base.BaseMviViewModel
import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.devmode.fcm.domain.model.FcmPriority
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class DevFcmPushState(
    val title: String = "",
    val priority: FcmPriority = FcmPriority.HIGH
): UiState

sealed interface DevFcmPushEffect : SideEffect

sealed interface DevFcmPushIntent : Intent {
    data class ShowPriorityBottomSheet(val isShown: Boolean) : DevFcmPushIntent

    data class UpdatePriority(val priority: FcmPriority) : DevFcmPushIntent
}

@HiltViewModel
class DevFcmPushViewModel @Inject constructor() : BaseMviViewModel<DevFcmPushState, DevFcmPushEffect, DevFcmPushIntent>(
    initialState = DevFcmPushState()
) {
    override fun processIntent(intent: DevFcmPushIntent) {

    }
}