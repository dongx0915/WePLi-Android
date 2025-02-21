package com.wepli.search.detail.mvi

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.uimodel.music.SongUiData

data class SearchDetailUiState(
    val searchInput: String = "",
    val searchMusicResult: List<SongUiData> = emptyList(),
    val selectedSongs: LinkedHashSet<SongUiData> = linkedSetOf(),
    val maxSelectCount: Int = 0
) : UiState

interface SearchDetailEffect : SideEffect {
    data class SearchError(val message: String) : SearchDetailEffect
    data class SelectedLimitExceeded(val limit: Int) : SearchDetailEffect
    data class NavigateBackWithResult(val selectedSongs: List<SongUiData>) : SearchDetailEffect
}

interface SearchDetailIntent : Intent {
    data class OnSearchQueryChanged(val query: String) : SearchDetailIntent
    data class RequestSearch(val query: String) : SearchDetailIntent
    data class OnSongSelected(val song: SongUiData) : SearchDetailIntent
    data class SetMaxSelectCount(val count: Int) : SearchDetailIntent
    data object OnCompleteSongSelect : SearchDetailIntent
}