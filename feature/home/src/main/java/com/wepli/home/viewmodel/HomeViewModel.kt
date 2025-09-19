package com.wepli.home.viewmodel

import android.util.Log
import base.BaseMviViewModel
import com.wepli.core.kotlin.flow.suspendCollectResult
import com.wepli.home.mvi.HomeEffect
import com.wepli.home.mvi.HomeIntent
import com.wepli.home.mvi.HomeUiState
import com.wepli.shared.feature.uimodel.artist.ArtistUiData
import com.wepli.shared.feature.uimodel.relaylist.RelaylistUiData
import com.wepli.uimodel.music.ChartMusicUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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

    private var timerJob: Job? = null

    init {
        getRelaylists()
        getTopChart()
        loadArtists()
        loadRecommendPlaylists()
        loadThemePlaylists()
    }

    override fun processIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadPlaylist -> loadPlaylistById(intent.playlistId)
            is HomeIntent.LoadRelaylist -> loadRelaylistById(intent.relaylistId)
            is HomeIntent.UpdateCurrentPage -> updateCurrentPage(intent.page)
        }
    }

    private fun updateCurrentPage(page: Int) = intent {
        val currentRelaylist = RelaylistUiData.fromDomain(state.relaylists[page])
        updateState {
            Log.d("페이지", "마감 시간: ${currentRelaylist.endDate} |남은 시간: ${currentRelaylist.remainingTime}")
            copy(currentRelaylistRemainingTime = currentRelaylist.remainingTime)
        }

        startTimer()
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

    private fun loadPlaylistById(id: Int) = launch {
        playlistRepository.getPlaylistById(id)
            .flowOn(Dispatchers.IO)
            .suspendCollectResult(
                onSuccess = {
                    postSideEffect { HomeEffect.PlaylistLoadSuccess(it.id) }
                },
                onFailure = {
                    Log.e("HomeViewModel", "loadPlaylistById: $it")
                    postSideEffect { HomeEffect.PlaylistLoadFailed }
                }
            )
    }

    private fun loadRelaylistById(id: Int) {
        launch {
            relaylistRepository.getRelaylistById(id)
                .flowOn(Dispatchers.IO)
                .suspendCollectResult(
                    onSuccess = {
                        postSideEffect { HomeEffect.RelaylistLoadSuccess(it.id) }
                    },
                    onFailure = {
                        Log.e("HomeViewModel", "loadRelaylistById: $it")
                        postSideEffect { HomeEffect.RelaylistLoadFailed }
                    }
                )
        }
    }

    private fun startTimer() = intent {
        timerJob?.cancel()
        timerJob = launch(Dispatchers.Default) {
            while (state.currentRelaylistRemainingTime >= 0L) {
                delay(1000)
                reduce {
                    state.copy(currentRelaylistRemainingTime = state.currentRelaylistRemainingTime - 1000)
                }
            }
        }
    }
}