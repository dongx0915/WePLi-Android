package com.wepli.home.viewmodel

import base.BaseMviViewModel
import com.wepli.core.kotlin.suspendCollectResult
import com.wepli.home.mvi.HomeEffect
import com.wepli.home.mvi.HomeIntent
import com.wepli.home.mvi.HomeUiState
import com.wepli.shared.feature.uimodel.artist.ArtistUiData
import com.wepli.uimodel.music.ChartMusicUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import repository.artist.ArtistRepository
import repository.chart.ChartRepository
import repository.playlist.PlaylistRepository
import repository.relaylist.RelaylistRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val chartRepository: ChartRepository,
    private val artistRepository: ArtistRepository,
    private val playlistRepository: PlaylistRepository,
    private val relaylistRepository: RelaylistRepository,
) : BaseMviViewModel<HomeUiState, HomeEffect, HomeIntent>(
    initialState = HomeUiState()
) {

    init {
        getRelaylists()
        getTopChart()
        loadArtists()
        loadRecommendPlaylists()
        loadThemePlaylists()
    }

    override fun processIntent(intent: HomeIntent) {
        // TODO: Implement
    }

    private fun getRelaylists() = intent {
        launchWithHandler {
            relaylistRepository.getRelaylists()
                .flowOn(Dispatchers.IO)
                .suspendCollectResult(
                    onSuccess = { relaylists ->
                        reduce { state.copy(relaylists = relaylists) }
                    }
                )
        }
    }

    private fun getTopChart() = intent {
        launch {
            chartRepository.getTopChart()
                .flowOn(Dispatchers.IO)
                .suspendCollectResult(
                    onSuccess = { topChartList ->
                        reduce {
                            state.copy(topChartList = topChartList.map(ChartMusicUiData::fromDomain))
                        }
                    }
                )
        }
    }

    private fun loadArtists() = intent {
        launch {
            artistRepository.getArtists()
                .flowOn(Dispatchers.IO)
                .suspendCollectResult(
                    onSuccess = { artistList ->
                        reduce {
                            state.copy(artistList = artistList.map(ArtistUiData::fromDomain))
                        }
                    }
                )
        }
    }

    private fun loadRecommendPlaylists() = intent {
        launch {
            playlistRepository.getRecommendPlaylist()
                .flowOn(Dispatchers.IO)
                .suspendCollectResult(
                    onSuccess = { playlists ->
                        reduce { state.copy(recommendPlaylists = playlists) }
                    }
                )
        }
    }

    private fun loadThemePlaylists() = intent {
        launch {
            playlistRepository.getThemePlaylist()
                .flowOn(Dispatchers.IO)
                .suspendCollectResult(
                    onSuccess = { playlists ->
                        reduce { state.copy(themePlaylists = playlists) }
                    }
                )
        }
    }
}