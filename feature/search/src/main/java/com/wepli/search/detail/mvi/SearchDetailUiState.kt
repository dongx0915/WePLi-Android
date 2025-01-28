package com.wepli.search.detail.mvi

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.uimodel.music.SongUiData

data class SearchDetailUiState(
    val searchInput: String = "",
    val searchMusicResult: List<SongUiData> = emptyList(),
    val selectedSongs: LinkedHashSet<SongUiData> = linkedSetOf()
) : UiState

interface SearchDetailEffect : SideEffect {
    data class SearchError(val message: String) : SearchDetailEffect
}

interface SearchDetailIntent : Intent {
    data class OnSearchQueryChanged(val query: String) : SearchDetailIntent
    data class RequestSearch(val query: String) : SearchDetailIntent
    data class OnSongSelected(val song: SongUiData) : SearchDetailIntent
}