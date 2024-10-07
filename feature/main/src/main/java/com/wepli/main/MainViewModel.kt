package com.wepli.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wepli.mock.recommendPlaylistMockData
import com.wepli.state.MainUiState
import repository.chart.ChartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import repository.artist.ArtistRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val chartRepository: ChartRepository,
    private val artistRepository: ArtistRepository,
) : ViewModel() {

    // MainState를 StateFlow로 관리
    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    init {
        getTopChart()
        loadArtists()
        loadRecommendPlaylists()
    }

    private fun getTopChart() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = chartRepository.getTopChart()

            _state.update {
                it.copy(topChartList = response)
            }
        }
    }

    private fun loadArtists() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = artistRepository.getArtists()

            _state.update {
                it.copy(artistList = response)
            }
        }
    }

    private fun loadRecommendPlaylists() {
        _state.update {
            it.copy(
                recommendPlaylists = recommendPlaylistMockData
            )
        }
    }
}