package com.wepli.devmode.fcm.presentation

import android.util.Log
import base.BaseMviViewModel
import com.wepli.devmode.fcm.core.code
import com.wepli.devmode.fcm.data.model.toFcmMessageRequest
import com.wepli.devmode.fcm.domain.model.FcmPriority
import com.wepli.devmode.fcm.domain.repository.DevModeFcmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import extensions.toPrettyJsonString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import javax.inject.Inject

@HiltViewModel
class DevFcmPushViewModel @Inject constructor(
    private val devModeFcmRepository: DevModeFcmRepository,
) : BaseMviViewModel<DevFcmPushState, DevFcmPushEffect, DevFcmPushIntent>(
    initialState = DevFcmPushState()
) {

    private var projectId: String? = null
    private var fcmToken: String? = null

    init {
        initialize()
    }

    override fun processIntent(intent: DevFcmPushIntent) {
        when (intent) {
            is DevFcmPushIntent.Init -> {
                this.projectId = intent.projectId
            }

            is DevFcmPushIntent.SendFcm -> sendFcmPush()

            is DevFcmPushIntent.ShowPriorityBottomSheet -> showPriorityBottomSheet(intent.isShown)

            is DevFcmPushIntent.UpdateTitle -> updateNotificationTitle(intent.title)

            is DevFcmPushIntent.UpdateDescription -> updateNotificationDesc(intent.description)

            is DevFcmPushIntent.UpdatePriority -> updatePriority(intent.priority)

            is DevFcmPushIntent.UpdatePushDataKey -> updatePushDataKey(intent.index, intent.key)

            is DevFcmPushIntent.UpdatePushDataValue -> updatePushDataValue(intent.index, intent.value)

            is DevFcmPushIntent.AddPushDataItem -> addPushDataItem()

            is DevFcmPushIntent.RemovePushDataItem -> removePushDataItem(intent.index)

            is DevFcmPushIntent.UploadJsonFile -> uploadJsonFile(intent.jsonContent)

            is DevFcmPushIntent.DeleteJsonFile -> deleteJsonFile()
        }
    }

    private fun initialize() = launch {
        val loadJsonJob = launch(Dispatchers.IO) {
            val json = devModeFcmRepository.getFirebaseAdminJson()
            updateState { copy(isJsonFileLoaded = json.isNotBlank()) }
        }

        val loadFcmTokenJob = launch(Dispatchers.IO) {
            runCatching { devModeFcmRepository.getFcmPushToken() }
                .onSuccess { token -> fcmToken = token }
                .onFailure { postSideEffect { DevFcmPushEffect.FcmTokenLoadFailed } }
        }

        joinAll(loadJsonJob, loadFcmTokenJob)
        updateState { copy(isLoading = false) }
    }

    private fun showPriorityBottomSheet(isShown: Boolean) = updateState {
        copy(isShownPriorityBottomSheet = isShown)
    }

    private fun sendFcmPush() = intent {
        launch(Dispatchers.IO) {
            runCatching {
                devModeFcmRepository.sendMessage(
                    projectId = projectId.orEmpty(),
                    accessToken = devModeFcmRepository.getFcmAccessToken(),
                    request = state
                        .getFcmMessage(fcmToken.orEmpty())
                        .toFcmMessageRequest()
                )
            }.onSuccess {
                postSideEffect { DevFcmPushEffect.SendFcmSuccess }
            }.onFailure {
                val error = when (it.code()) {
                    400 -> DevFcmPushEffect.FcmTokenValidError
                    401 -> DevFcmPushEffect.ApiKeyValidError
                    else -> DevFcmPushEffect.UnknownError(it.code())
                }

                postSideEffect { error }
            }
        }
    }

    private fun uploadJsonFile(jsonContent: String) = launch(Dispatchers.IO) {
        devModeFcmRepository.saveFirebaseAdminJson(jsonContent)
        updateState { copy(isJsonFileLoaded = true) }
    }

    private fun deleteJsonFile() = launch(Dispatchers.IO) {
        devModeFcmRepository.saveFirebaseAdminJson("")
        updateState { copy(isJsonFileLoaded = false) }
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

    private fun updateNotificationTitle(title: String) = updateState {
        copy(title = title)
    }

    private fun updateNotificationDesc(description: String) = updateState {
        copy(description = description)
    }

    private fun updatePriority(priority: FcmPriority) = updateState {
        copy(priority = priority)
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