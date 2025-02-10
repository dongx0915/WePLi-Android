package com.wepli.feature.namecard.detail.viewmodel

import androidx.lifecycle.viewModelScope
import base.BaseMviViewModel
import com.wepli.feature.namecard.detail.mvi.NameCardDetailEffect
import com.wepli.feature.namecard.detail.mvi.NameCardDetailIntent
import com.wepli.feature.namecard.detail.mvi.NameCardDetailUiState
import com.wepli.uimodel.music.SongUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextLong

@HiltViewModel
class NameCardDetailViewModel @Inject constructor() : BaseMviViewModel<NameCardDetailUiState, NameCardDetailEffect, NameCardDetailIntent>(
    initialState = NameCardDetailUiState()
) {
    override fun processIntent(intent: NameCardDetailIntent) {
        when(intent) {
            is NameCardDetailIntent.Initialize -> handleInitialize(intent.totalPage, intent.oneLineIntroMaxLength)
            is NameCardDetailIntent.OnChangedOneLineIntro -> handleChangedOneLineIntro(intent.text)
            is NameCardDetailIntent.OnChangedInstagramId -> handleChangedInstagramId(intent.text)
            is NameCardDetailIntent.OnFavoriteSongSelected -> handleFavoriteSongSelected(intent.song)
            is NameCardDetailIntent.OnNextPage -> handleNextPage()
            is NameCardDetailIntent.OnPreviousPage -> handlePreviousPage()
        }
    }

    private fun handleInitialize(totalPage: Int, oneLineIntroMaxLength: Int) {
        updateState {
            copy(
                totalPage = totalPage,
                oneLineIntro = NameCardDetailUiState.FieldState(maxLength = oneLineIntroMaxLength)
            )
        }
    }

    private fun handleChangedOneLineIntro(text: String) {
        updateState { copy(oneLineIntro = oneLineIntro.copy(text = text)) }
    }

    private fun handleChangedInstagramId(text: String) {
        updateState { copy(instagramId = instagramId.copy(text = text)) }
    }

    private fun handleFavoriteSongSelected(song: SongUiData) {
        updateState { copy(selectedFavoriteSong = song) }
    }

    private fun handleNextPage() = intent {
        if (state.isLastPage) {
            makeNameCard()
        } else {
            updateState { setNextPage() }
        }
    }

    private fun handlePreviousPage() {
        updateState { setPreviousPage() }
    }

    private fun makeNameCard() = intent {
        reduce { state.copy(isLoading = true) }

        withContext(Dispatchers.Default) {
            while (state.makeCardProgress < 1.0f) {
                reduce {
                    val randomIncrement = Random.nextDouble(0.05, 0.3).toFloat()
                    val progress = state.makeCardProgress + randomIncrement

                    state.updateProgress(progress)
                }

                delay(Random.nextLong(250L .. 750))
            }
        }

        reduce { state.copy(isLoading = false, makeCardProgress = 0f) }
    }
}