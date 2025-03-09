package com.wepli.relaylist.detail

import android.util.Log
import base.BaseMviViewModel
import com.wepli.core.kotlin.flow.suspendCollectResult
import com.wepli.relaylist.detail.mvi.RelaylistDetailEffect
import com.wepli.relaylist.detail.mvi.RelaylistDetailIntent
import com.wepli.relaylist.detail.mvi.RelaylistDetailUiState
import com.wepli.shared.feature.uimodel.relaylist.RelaylistUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import repository.relaylist.RelaylistRepository
import javax.inject.Inject


@HiltViewModel
class RelaylistDetailViewModel @Inject constructor(
    private val relaylistRepository: RelaylistRepository
) : BaseMviViewModel<RelaylistDetailUiState, RelaylistDetailEffect, RelaylistDetailIntent>(
    initialState = RelaylistDetailUiState()
) {

    override fun processIntent(intent: RelaylistDetailIntent) {
        when (intent) {
            is RelaylistDetailIntent.LoadRelaylist -> loadRelaylist(intent.relaylistId)
        }
    }

    private fun loadRelaylist(relaylistId: Int) = launch {
        relaylistRepository.getRelaylistById(relaylistId)
            .suspendCollectResult(
                onSuccess = {
                    updateState {
                        val relaylist = RelaylistUiData.fromDomain(it)
                        copy(
                            relaylist = relaylist,
                            remainingTime = relaylist.remainingTime
                        )
                    }

                    startTimer()
                },
                onFailure = {
                    Log.e("RelaylistDetailViewModel", "loadRelaylist: $it")
                }
            )
    }

    private fun startTimer() = intent {
        withContext(Dispatchers.Default){
            while(state.remainingTime >= 0L) {
                delay(1000)
                updateState {
                    copy(remainingTime = state.remainingTime - 1000)
                }
            }
        }
    }
}