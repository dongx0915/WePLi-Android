package com.wepli.feature.namecard.detail.mvi

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.shared.feature.uimodel.namecard.PhotoCardUiData
import com.wepli.uimodel.music.SongUiData

// TODO Page 별로 별도의 State를 갖는게 나을지
data class NameCardDetailUiState(
    val currentPage: Int = 0,
    val totalPage: Int = 0,
    val oneLineIntro: FieldState = FieldState(),
    val instagramId: FieldState = FieldState(),
    val selectedFavoriteSong: SongUiData? = null,
    val makeCardProgress: Float = 0f,
    val isLoading: Boolean = false,
) : UiState {

    data class FieldState(
        val text: String = "",
        val maxLength: Int = Int.MAX_VALUE
    ) {
        val isLengthExceed: Boolean
            get() = text.length > maxLength
    }

    val isLastPage: Boolean
        get() = currentPage == totalPage - 1

    fun setNextPage(): NameCardDetailUiState {
        return copy(currentPage = (currentPage + 1).coerceIn(0, totalPage))
    }

    fun setPreviousPage(): NameCardDetailUiState {
        return copy(currentPage = (currentPage - 1).coerceIn(0, totalPage))
    }

    fun updateProgress(progress: Float): NameCardDetailUiState {
        return copy(makeCardProgress = progress.coerceIn(0f, 1f))
    }
}

interface NameCardDetailEffect : SideEffect {
    data object NavigateBack : NameCardDetailEffect
    data class OnCompleteChapter(val nameCardResult: PhotoCardUiData) : NameCardDetailEffect
}

// TODO 페이지 별로 Intent 주석으로 정리하기
interface NameCardDetailIntent : Intent {
    data class Initialize(val totalPage: Int, val oneLineIntroMaxLength: Int) : NameCardDetailIntent
    data class OnChangedOneLineIntro(val text: String) : NameCardDetailIntent
    data class OnChangedInstagramId(val text: String) : NameCardDetailIntent
    data class OnFavoriteSongSelected(val song: SongUiData) : NameCardDetailIntent
    data object OnNextPage : NameCardDetailIntent
    data object OnPreviousPage : NameCardDetailIntent
}