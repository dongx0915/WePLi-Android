package com.wepli.home.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wepli.home.state.HomeUiState
import repository.chart.ChartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import extensions.collectResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import repository.artist.ArtistRepository
import repository.playlist.PlaylistRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val chartRepository: ChartRepository,
    private val artistRepository: ArtistRepository,
    private val playlistRepository: PlaylistRepository,
) : ViewModel() {

    // MainState를 StateFlow로 관리
    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    init {
        getTopChart()
        loadArtists()
        loadRecommendPlaylists()
        loadThemePlaylists()
    }

    private fun getTopChart() = viewModelScope.launch {
        chartRepository.getTopChart()
            .flowOn(Dispatchers.IO)
            .collectResult(
                onSuccess = { topChartList ->
                    _state.update {
                        it.copy(topChartList = topChartList)
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
                        it.copy(artistList = artistList)
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