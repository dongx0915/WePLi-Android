package com.wepli.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wepli.mock.artistMockData
import com.wepli.mock.recommendPlaylistMockData
import repository.chart.ChartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.artist.Artist
import model.music.ChartMusic
import model.playlist.RecommendPlaylist
import javax.inject.Inject

data class MainState(
    val topChartList: List<ChartMusic> = emptyList(),
    val artistList: List<Artist> = emptyList(),
    val recommendPlaylists: List<RecommendPlaylist> = emptyList()
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val chartRepository: ChartRepository
) : ViewModel() {

    // MainState를 StateFlow로 관리
    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

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
        _state.update {
            it.copy(
                artistList = artistMockData
            )
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