package com.wepli.devmode.fcm.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import base.BaseMviViewModel
import base.Intent
import base.SideEffect
import base.UiState
import com.google.firebase.messaging.FirebaseMessaging
import com.wepli.devmode.fcm.core.code
import com.wepli.devmode.fcm.data.model.toFcmMessageRequest
import com.wepli.devmode.fcm.domain.model.FcmMessage
import com.wepli.devmode.fcm.domain.model.FcmPriority
import com.wepli.devmode.fcm.domain.repository.DevModeFcmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import extensions.toPrettyJsonString
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resumeWithException

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
    data object AuthorizationError : DevFcmPushEffect

    data class UnknownError(val code: Int) : DevFcmPushEffect
}

sealed interface DevFcmPushIntent : Intent {

    data class Init(val projectId: String) : DevFcmPushIntent
    data object SendFcm : DevFcmPushIntent
    data object AddPushDataItem : DevFcmPushIntent
    data class RemovePushDataItem(val index: Int) : DevFcmPushIntent

    data class ShowPriorityBottomSheet(val isShown: Boolean) : DevFcmPushIntent
    data class UpdatePriority(val priority: FcmPriority) : DevFcmPushIntent
    data class UpdatePushDataKey(val index: Int, val key: String) : DevFcmPushIntent
    data class UpdatePushDataValue(val index: Int, val value: String) : DevFcmPushIntent

    data class UploadJsonFile(val jsonContent: String) : DevFcmPushIntent
    data object DeleteJsonFile : DevFcmPushIntent
}

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

            is DevFcmPushIntent.SendFcm -> {
                sendFcmPush()
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

            is DevFcmPushIntent.UploadJsonFile -> {
                uploadJsonFile(intent.jsonContent)
            }

            DevFcmPushIntent.DeleteJsonFile -> {
                deleteJsonFile()
            }
        }
    }

    private fun initialize() = launch {
        val loadJsonJob = launch(Dispatchers.IO) {
            val json = devModeFcmRepository.getFirebaseAdminJson()
            updateState { copy(isJsonFileLoaded = json.isNotBlank()) }
        }

        val loadFcmTokenJob = launch(Dispatchers.IO) {
            runCatching { getFcmPushToken() }
                .onSuccess { token -> fcmToken = token }
                .onFailure { postSideEffect { DevFcmPushEffect.FcmTokenLoadFailed } }
        }

        joinAll(loadJsonJob, loadFcmTokenJob)
        updateState { copy(isLoading = false) }
    }

    private fun uploadJsonFile(jsonContent: String) = launch(Dispatchers.IO) {
        devModeFcmRepository.saveFirebaseAdminJson(jsonContent)
        updateState { copy(isJsonFileLoaded = true) }
    }

    private fun deleteJsonFile() = launch(Dispatchers.IO) {
        devModeFcmRepository.saveFirebaseAdminJson("")
        updateState { copy(isJsonFileLoaded = false) }
    }

    private suspend fun getFcmPushToken(): String = withContext(Dispatchers.IO) {
        suspendCancellableCoroutine {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    it.resumeWithException(
                        CancellationException("failed load fcm token")
                    )
                    return@addOnCompleteListener
                }

                val result = if (task.result != null) {
                    Result.success(task.result)
                } else {
                    Result.failure(Exception("fcm token is null"))
                }

                it.resumeWith(result = result)
            }
        }
    }

    private fun sendFcmPush() = intent {
        launch(Dispatchers.IO) {
            runCatching {
                devModeFcmRepository.sendMessage(
                    projectId = projectId.orEmpty(),
                    accessToken = devModeFcmRepository.getFcmAccessToken(),
                    request = state
                        .getFcmMessage(fcmToken ?: getFcmPushToken())
                        .toFcmMessageRequest()
                )
            }.onSuccess {
                Log.d("성공", it.toString())
                postSideEffect { DevFcmPushEffect.SendFcmSuccess }
            }.onFailure {
                Log.e("실패", it.toString())
                val error = when (it.code()) {
                    401 -> DevFcmPushEffect.AuthorizationError
                    else -> DevFcmPushEffect.UnknownError(it.code())
                }

                postSideEffect { error }
            }
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