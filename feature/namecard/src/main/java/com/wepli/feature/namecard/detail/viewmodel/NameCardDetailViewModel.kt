package com.wepli.feature.namecard.detail.viewmodel

import base.BaseMviViewModel
import com.wepli.feature.namecard.detail.mvi.NameCardDetailEffect
import com.wepli.feature.namecard.detail.mvi.NameCardDetailIntent
import com.wepli.feature.namecard.detail.mvi.NameCardDetailUiState
import com.wepli.uimodel.music.SongUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NameCardDetailViewModel @Inject constructor() : BaseMviViewModel<NameCardDetailUiState, NameCardDetailEffect, NameCardDetailIntent>(
    initialState = NameCardDetailUiState()
) {
    override fun processIntent(intent: NameCardDetailIntent) {
        when(intent) {
            is NameCardDetailIntent.Initialize -> handleInitialize(intent.totalPage, intent.oneLineIntroMaxLength)
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

    private fun handleFavoriteSongSelected(song: SongUiData) {
        updateState { copy(selectedFavoriteSong = song) }
    }

    private fun handleNextPage() {
        updateState { setNextPage() }
    }

    private fun handlePreviousPage() {
        updateState { setPreviousPage() }
    }
}