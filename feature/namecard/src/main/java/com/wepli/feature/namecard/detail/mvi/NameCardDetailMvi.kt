package com.wepli.feature.namecard.detail.mvi

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.uimodel.music.SongUiData

data class NameCardDetailUiState(
    private val _currentPage: Int = 0,
    val totalPage: Int = 4,
    val selectedFavoriteSong: SongUiData? = null
) : UiState {
    val currentPage: Int
        get() = _currentPage

    fun setNextPage(): NameCardDetailUiState {
        return copy(_currentPage = (currentPage + 1).coerceIn(0, totalPage))
    }

    fun setPreviousPage(): NameCardDetailUiState {
        return copy(_currentPage = (currentPage - 1).coerceIn(0, totalPage))
    }
}

interface NameCardDetailEffect : SideEffect {

}

interface NameCardDetailIntent : Intent {
    data class OnFavoriteSongSelected(val song: SongUiData) : NameCardDetailIntent
    data object OnNextPage : NameCardDetailIntent
    data object OnPreviousPage : NameCardDetailIntent
    data object OnCompleteChapter : NameCardDetailIntent
    data object NavigateToBack : NameCardDetailIntent
}