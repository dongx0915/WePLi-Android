package com.wepli.feature.namecard.detail.mvi

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.uimodel.music.SongUiData

// TODO Page 별로 별도의 State를 갖는게 나을지
data class NameCardDetailUiState(
    private val _currentPage: Int = 0,
    val totalPage: Int = 0,
    val oneLineIntro: FieldState = FieldState(),
    val instagramId: FieldState = FieldState(),
    val selectedFavoriteSong: SongUiData? = null
) : UiState {

    data class FieldState(
        val text: String = "",
        val maxLength: Int = 0
    ) {
        val isLengthExceed: Boolean
            get() = text.length > maxLength
    }

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

// TODO 페이지 별로 Intent 주석으로 정리하기
interface NameCardDetailIntent : Intent {
    data class Initialize(val totalPage: Int, val oneLineIntroMaxLength: Int) : NameCardDetailIntent
    data class OnChangedOneLineIntro(val text: String) : NameCardDetailIntent
    data class OnFavoriteSongSelected(val song: SongUiData) : NameCardDetailIntent
    data object OnNextPage : NameCardDetailIntent
    data object OnPreviousPage : NameCardDetailIntent
    data object OnCompleteChapter : NameCardDetailIntent
}