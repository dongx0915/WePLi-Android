package com.wepli.home.state

import model.artist.Artist
import model.music.ChartMusic
import model.playlist.RecommendPlaylist

data class HomeUiState(
    val topChartList: List<ChartMusic> = emptyList(),
    val artistList: List<Artist> = emptyList(),
    val recommendPlaylists: List<RecommendPlaylist> = emptyList(),
    val themePlaylists: List<RecommendPlaylist> = emptyList()
)
