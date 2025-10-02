package com.wepli.devmode.fcm.presentation

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.devmode.fcm.domain.model.FcmMessage
import com.wepli.devmode.fcm.domain.model.FcmPriority


data class DevFcmPushState(
    val title: String = "",
    val description: String = "",
    val priority: FcmPriority = FcmPriority.HIGH,
    val pushDataItems: List<Pair<String, String>> = listOf("" to ""),

    val isJsonFileLoaded: Boolean = false,
    val isLoading: Boolean = false,
    val isShownPriorityBottomSheet: Boolean = false
) : UiState {

    fun getFcmMessage(token: String): FcmMessage {
        return FcmMessage(
            message = FcmMessage.Message(
                token = token,
                notification = FcmMessage.Notification(
                    title = title,
                    body = description,
                    image = "",
                ),
                android = FcmMessage.AndroidConfig(
                    priority = priority
                ),
                data = pushDataItems.toMap()
            )
        )
    }
}

sealed interface DevFcmPushEffect : SideEffect {

    data object SendFcmSuccess : DevFcmPushEffect
    data object FcmTokenLoadFailed : DevFcmPushEffect
    data object FcmTokenValidError : DevFcmPushEffect
    data object ApiKeyValidError : DevFcmPushEffect

    data class UnknownError(val code: Int) : DevFcmPushEffect
}

sealed interface DevFcmPushIntent : Intent {

    data class Init(val projectId: String) : DevFcmPushIntent
    data object SendFcm : DevFcmPushIntent
    data object AddPushDataItem : DevFcmPushIntent
    data class RemovePushDataItem(val index: Int) : DevFcmPushIntent

    data class ShowPriorityBottomSheet(val isShown: Boolean) : DevFcmPushIntent

    data class UpdateTitle(val title: String) : DevFcmPushIntent
    data class UpdateDescription(val description: String) : DevFcmPushIntent
    data class UpdatePriority(val priority: FcmPriority) : DevFcmPushIntent
    data class UpdatePushDataKey(val index: Int, val key: String) : DevFcmPushIntent
    data class UpdatePushDataValue(val index: Int, val value: String) : DevFcmPushIntent

    data class UploadJsonFile(val jsonContent: String) : DevFcmPushIntent
    data object DeleteJsonFile : DevFcmPushIntent
}
