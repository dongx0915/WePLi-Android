package com.wepli.devmode.fcm.presentation

import android.util.Log
import androidx.compose.ui.graphics.PathMeasure
import base.BaseMviViewModel
import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.devmode.fcm.data.model.toFcmMessageRequest
import com.wepli.devmode.fcm.domain.model.FcmMessage
import com.wepli.devmode.fcm.domain.model.FcmPriority
import com.wepli.devmode.fcm.domain.repository.DevModeFcmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import extensions.toPrettyJsonString
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

data class DevFcmPushState(
    val title: String = "",
    val description: String = "",
    val priority: FcmPriority = FcmPriority.HIGH,

    val pushDataItems: List<Pair<String, String>> = listOf("" to ""),
    val isShownPriorityBottomSheet: Boolean = false
) : UiState

sealed interface DevFcmPushEffect : SideEffect

sealed interface DevFcmPushIntent : Intent {

    data class Init(val fcmToken: String) : DevFcmPushIntent
    data object SendFcm : DevFcmPushIntent
    data object AddPushDataItem : DevFcmPushIntent
    data class RemovePushDataItem(val index: Int) : DevFcmPushIntent

    data class ShowPriorityBottomSheet(val isShown: Boolean) : DevFcmPushIntent
    data class UpdatePriority(val priority: FcmPriority) : DevFcmPushIntent
    data class UpdatePushDataKey(val index: Int, val key: String) : DevFcmPushIntent
    data class UpdatePushDataValue(val index: Int, val value: String) : DevFcmPushIntent
}

@HiltViewModel
class DevFcmPushViewModel @Inject constructor(
    private val devModeFcmRepository: DevModeFcmRepository,
) : BaseMviViewModel<DevFcmPushState, DevFcmPushEffect, DevFcmPushIntent>(
    initialState = DevFcmPushState()
) {

    private var fcmToken: String = ""

    override fun processIntent(intent: DevFcmPushIntent) {
        when (intent) {
            is DevFcmPushIntent.Init -> {
                this.fcmToken = intent.fcmToken
            }

            is DevFcmPushIntent.ShowPriorityBottomSheet -> {
                updateState {
                    copy(isShownPriorityBottomSheet = intent.isShown)
                }
            }

            is DevFcmPushIntent.UpdatePriority -> {
                updateState {
                    copy(priority = intent.priority)
                }
            }

            is DevFcmPushIntent.UpdatePushDataKey -> {
                updatePushDataKey(intent.index, intent.key)
            }

            is DevFcmPushIntent.UpdatePushDataValue -> {
                updatePushDataValue(intent.index, intent.value)
            }

            DevFcmPushIntent.AddPushDataItem -> {
                addPushDataItem()
            }

            is DevFcmPushIntent.RemovePushDataItem -> {
                removePushDataItem(intent.index)
            }

            DevFcmPushIntent.SendFcm -> sendFcmPush()
        }
    }

    private fun sendFcmPush() = intent {
        launch(Dispatchers.IO) {
            devModeFcmRepository.sendMessage(
                projectId = "wepli-app-49e90",
                accessToken = devModeFcmRepository.getFcmAccessToken(),
                request = FcmMessage(
                    message = FcmMessage.Message(
                        token = fcmToken,
                        notification = FcmMessage.Notification(
                            title = state.title,
                            body = state.description,
                            image = "",
                        ),
                        android = FcmMessage.AndroidConfig(
                            priority = state.priority
                        ),
                        data = state.pushDataItems.toMap()
                    )
                ).toFcmMessageRequest()
            )
        }
    }

    private fun addPushDataItem() = intent {
        val pushDataItems = state.pushDataItems.toMutableList().apply {
            add("" to "")
        }

        reduce {
            state.copy(pushDataItems = pushDataItems)
        }
    }

    private fun removePushDataItem(index: Int) = intent {
        val pushDataItems = state.pushDataItems.toMutableList().apply {
            removeAt(index)
        }

        reduce {
            state.copy(pushDataItems = pushDataItems)
        }
    }

    private fun updatePushDataKey(index: Int, key: String) = intent {
        val targetData = state.pushDataItems.getOrNull(index) ?: return@intent
        val newData = state.pushDataItems.toMutableList().apply {
            set(index, targetData.copy(first = key))
        }

        reduce {
            state.copy(pushDataItems = newData).apply {
                Log.d("스테이트", state.toPrettyJsonString())
            }
        }
    }

    private fun updatePushDataValue(index: Int, value: String) = intent {
        val targetData = state.pushDataItems.getOrNull(index) ?: return@intent
        val newData = state.pushDataItems.toMutableList().apply {
            set(index, targetData.copy(second = value))
        }

        reduce {
            state.copy(pushDataItems = newData).apply {
                Log.d("스테이트", state.toPrettyJsonString())
            }
        }
    }
}