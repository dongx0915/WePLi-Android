package com.wepli.home.mvi

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.uimodel.artist.ArtistUiData
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

interface HomeEffect : SideEffect

interface HomeIntent : Intent
