package com.wepli.home.mvi

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.shared.feature.mock.relaylistMockData
import com.wepli.shared.feature.uimodel.artist.ArtistUiData
import com.wepli.uimodel.music.ChartMusicUiData
import model.playlist.RecommendPlaylist
import model.relaylist.Relaylist

data class HomeUiState(
    val relaylists: List<Relaylist> = relaylistMockData,
    val currentRelaylistRemainingTime:Long = 0L,
    val topChartList: List<ChartMusicUiData> = emptyList(),
    val artistList: List<ArtistUiData> = emptyList(),
    val recommendPlaylists: List<RecommendPlaylist> = emptyList(),
    val themePlaylists: List<RecommendPlaylist> = emptyList()
) : UiState

sealed interface HomeEffect : SideEffect {
    data class PlaylistLoadSuccess(val playlistId: Int) : HomeEffect
    data object PlaylistLoadFailed : HomeEffect

    data class RelaylistLoadSuccess(val relaylistId: Int) : HomeEffect
    data object RelaylistLoadFailed : HomeEffect
}

sealed interface HomeIntent : Intent {
    data class LoadPlaylist(val playlistId: Int) : HomeIntent
    data class LoadRelaylist(val relaylistId: Int) : HomeIntent

    data class UpdateCurrentPage(val page: Int) : HomeIntent
}
