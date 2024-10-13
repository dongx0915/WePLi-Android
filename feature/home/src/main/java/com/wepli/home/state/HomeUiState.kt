package com.wepli.home.state

import com.wepli.uimodel.artist.ArtistUiData
import com.wepli.uimodel.music.ChartMusicUiData
import model.artist.Artist
import model.music.ChartMusic
import model.playlist.RecommendPlaylist

data class HomeUiState(
    val topChartList: List<ChartMusicUiData> = emptyList(),
    val artistList: List<ArtistUiData> = emptyList(),
    val recommendPlaylists: List<RecommendPlaylist> = emptyList(),
    val themePlaylists: List<RecommendPlaylist> = emptyList()
)
