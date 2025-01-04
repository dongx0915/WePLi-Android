package com.wepli.search.state

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.uimodel.music.SongUiData

data class SearchUiState(
    val searchInput: String = "",
    val searchMusicResult: List<SongUiData> = emptyList(),
) : UiState

interface SearchEffect : SideEffect {
    data class SearchError(val message: String) : SearchEffect
}

interface SearchIntent : Intent {
    data class OnSearchQueryChanged(val query: String) : SearchIntent
    data class RequestSearch(val query: String) : SearchIntent
}