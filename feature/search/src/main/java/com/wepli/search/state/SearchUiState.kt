package com.wepli.search.state

import base.Intent
import base.SideEffect
import base.UiState
import model.music.Song

data class SearchUiState(
    val searchInput: String = "",
    val searchMusicResult: List<Song> = emptyList(),
) : UiState

interface SearchEffect : SideEffect

interface SearchIntent : Intent {
    data class OnSearchQueryChanged(val query: String) : SearchIntent
    data class RequestSearch(val query: String) : SearchIntent
}