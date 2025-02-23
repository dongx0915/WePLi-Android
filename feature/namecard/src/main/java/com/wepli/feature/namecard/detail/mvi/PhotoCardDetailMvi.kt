package com.wepli.feature.namecard.detail.mvi

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.shared.feature.uimodel.namecard.PhotoCardUiData
import com.wepli.uimodel.music.SongUiData

// TODO Page 별로 별도의 State를 갖는게 나을지
data class PhotoCardDetailUiState(
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

    fun setNextPage(): PhotoCardDetailUiState {
        return copy(currentPage = (currentPage + 1).coerceIn(0, totalPage))
    }

    fun setPreviousPage(): PhotoCardDetailUiState {
        return copy(currentPage = (currentPage - 1).coerceIn(0, totalPage))
    }

    fun updateProgress(progress: Float): PhotoCardDetailUiState {
        return copy(makeCardProgress = progress.coerceIn(0f, 1f))
    }
}

interface PhotoCardDetailEffect : SideEffect {
    data object NavigateBack : PhotoCardDetailEffect
    data class OnCompleteChapter(val photoCardResult: PhotoCardUiData) : PhotoCardDetailEffect
}

// TODO 페이지 별로 Intent 주석으로 정리하기
interface PhotoCardDetailIntent : Intent {
    data class Initialize(val totalPage: Int, val oneLineIntroMaxLength: Int) : PhotoCardDetailIntent
    data class OnChangedOneLineIntro(val text: String) : PhotoCardDetailIntent
    data class OnChangedInstagramId(val text: String) : PhotoCardDetailIntent
    data class OnFavoriteSongSelected(val song: SongUiData) : PhotoCardDetailIntent
    data object OnNextPage : PhotoCardDetailIntent
    data object OnPreviousPage : PhotoCardDetailIntent
}