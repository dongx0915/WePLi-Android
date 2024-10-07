package com.wepli.state

import model.artist.Artist
import model.music.ChartMusic
import model.playlist.RecommendPlaylist

data class MainUiState(
    val topChartList: List<ChartMusic> = emptyList(),
    val artistList: List<Artist> = emptyList(),
    val recommendPlaylists: List<RecommendPlaylist> = emptyList()
)
