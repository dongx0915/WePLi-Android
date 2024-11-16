package com.wepli.home.state

import com.wepli.shared.feature.mock.relaylistMockData
import com.wepli.uimodel.artist.ArtistUiData
import com.wepli.uimodel.music.ChartMusicUiData
import model.playlist.RecommendPlaylist
import model.relaylist.Relaylist

data class HomeUiState(
    val relaylists: List<Relaylist> = relaylistMockData,
    val topChartList: List<ChartMusicUiData> = emptyList(),
    val artistList: List<ArtistUiData> = emptyList(),
    val recommendPlaylists: List<RecommendPlaylist> = emptyList(),
    val themePlaylists: List<RecommendPlaylist> = emptyList()
)
