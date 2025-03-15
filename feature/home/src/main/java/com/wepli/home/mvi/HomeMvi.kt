package com.wepli.home.mvi

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.shared.feature.uimodel.artist.ArtistUiData
import com.wepli.uimodel.music.ChartMusicUiData
import model.playlist.RecommendPlaylist
import model.relaylist.Relaylist

data class HomeUiState(
    val relaylists: List<Relaylist> = emptyList(),
    val topChartList: List<ChartMusicUiData> = emptyList(),
    val artistList: List<ArtistUiData> = emptyList(),
    val recommendPlaylists: List<RecommendPlaylist> = emptyList(),
    val themePlaylists: List<RecommendPlaylist> = emptyList()
) : UiState

sealed interface HomeEffect : SideEffect {
    data class RelaylistLoadSuccess(val relaylistId: Int) : HomeEffect
    data object RelaylistLoadFailed : HomeEffect
}

sealed interface HomeIntent : Intent {
    data class LoadRelaylist(val relaylistId: Int) : HomeIntent
}
