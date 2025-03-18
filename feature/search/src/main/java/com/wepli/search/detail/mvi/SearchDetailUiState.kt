package com.wepli.search.detail.mvi

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.uimodel.music.SongUiData

data class SearchDetailUiState(
    val searchInput: String = "",
    val searchMusicResult: List<SongUiData> = emptyList(),
    val selectedSongs: LinkedHashSet<SongUiData> = linkedSetOf(),
    val songInfo: SongUiData? = null,
    val maxSelectCount: Int = 0
) : UiState

sealed interface SearchDetailEffect : SideEffect {
    data class SearchError(val message: String) : SearchDetailEffect
    data class SelectedLimitExceeded(val limit: Int) : SearchDetailEffect
    data class NavigateBackWithResult(val selectedSongs: List<SongUiData>) : SearchDetailEffect
    data object NavigateToSongInfo : SearchDetailEffect
}

sealed interface SearchDetailIntent : Intent {
    data class OnSearchQueryChanged(val query: String) : SearchDetailIntent
    data class RequestSearch(val query: String) : SearchDetailIntent
    data class OnSongSelected(val song: SongUiData) : SearchDetailIntent
    data class SetMaxSelectCount(val count: Int) : SearchDetailIntent
    data object OnCompleteSongSelect : SearchDetailIntent
    data class ShowSongInfoBottomSheet(val selectedSong: SongUiData?) : SearchDetailIntent
    data object LoadSongInfo : SearchDetailIntent
}