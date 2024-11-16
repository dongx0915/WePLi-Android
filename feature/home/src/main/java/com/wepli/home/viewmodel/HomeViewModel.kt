package com.wepli.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import base.BaseViewModel
import com.wepli.home.state.HomeUiState
import repository.chart.ChartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import com.wepli.core.kotlin.collectResult
import com.wepli.uimodel.artist.ArtistUiData
import com.wepli.uimodel.music.ChartMusicUiData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import repository.artist.ArtistRepository
import repository.playlist.PlaylistRepository
import repository.relaylist.RelaylistRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val chartRepository: ChartRepository,
    private val artistRepository: ArtistRepository,
    private val playlistRepository: PlaylistRepository,
    private val relaylistRepository: RelaylistRepository,
) : BaseViewModel() {

    // MainState를 StateFlow로 관리
    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    init {
        getRelaylists()
        getTopChart()
        loadArtists()
        loadRecommendPlaylists()
        loadThemePlaylists()
    }

    private fun getRelaylists() = launchWithHandler {
        relaylistRepository.getRelaylists()
            .flowOn(Dispatchers.IO)
            .collectResult(
                onSuccess = { relaylists ->
                    _state.update {
                        it.copy(relaylists = relaylists)
                    }
                }
            )
    }

    private fun getTopChart() = viewModelScope.launch {
        chartRepository.getTopChart()
            .flowOn(Dispatchers.IO)
            .collectResult(
                onSuccess = { topChartList ->
                    _state.update {
                        it.copy(topChartList = topChartList.map(ChartMusicUiData::fromDomain))
                    }
                }
            )
    }

    private fun loadArtists() = viewModelScope.launch {
        artistRepository.getArtists()
            .flowOn(Dispatchers.IO)
            .collectResult(
                onSuccess = { artistList ->
                    _state.update {
                        it.copy(artistList = artistList.map(ArtistUiData::fromDomain))
                    }
                }
            )
    }

    private fun loadRecommendPlaylists() = viewModelScope.launch {
        playlistRepository.getRecommendPlaylist()
            .flowOn(Dispatchers.IO)
            .collectResult(
                onSuccess = { playlists ->
                    _state.update {
                        it.copy(recommendPlaylists = playlists)
                    }
                }
            )
    }

    private fun loadThemePlaylists() = viewModelScope.launch {
        playlistRepository.getThemePlaylist()
            .flowOn(Dispatchers.IO)
            .collectResult(
                onSuccess = { playlists ->
                    _state.update {
                        it.copy(themePlaylists = playlists)
                    }
                }
            )
    }
}