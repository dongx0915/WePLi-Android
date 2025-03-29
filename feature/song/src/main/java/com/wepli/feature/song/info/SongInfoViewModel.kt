package com.wepli.feature.song.info

import android.util.Log
import base.BaseMviViewModel
import com.wepli.core.kotlin.flow.collectResult
import com.wepli.core.kotlin.flow.suspendCollectResult
import com.wepli.feature.song.info.mvi.SongInfoEffect
import com.wepli.feature.song.info.mvi.SongInfoIntent
import com.wepli.feature.song.info.mvi.SongInfoUiState
import com.wepli.shared.feature.uimodel.album.AlbumUiData
import com.wepli.uimodel.music.SongUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import repository.applemusic.AppleMusicRepository
import javax.inject.Inject

@HiltViewModel
class SongInfoViewModel @Inject constructor(
    private val appleMusicRepository: AppleMusicRepository
) : BaseMviViewModel<SongInfoUiState, SongInfoEffect, SongInfoIntent>(
    initialState = SongInfoUiState()
) {

    override fun processIntent(intent: SongInfoIntent) {
        when (intent) {
            is SongInfoIntent.Init -> init(intent.song)
        }
    }

    private fun init(song: SongUiData) = intent {
        updateState { copy(song = song) }

        launch {
            appleMusicRepository.getSongById(song.id)
                .suspendCollectResult(
                    onSuccess = {
                        getAlbumById(it.albumId.orEmpty())
                    },
                    onFailure = {
                        Log.e("SongInfoViewModel", it.message ?: "Error")
                    }
                )
        }
    }

    private suspend fun getAlbumById(albumId: String) = intent {
        appleMusicRepository.getAlbumById(albumId)
            .flowOn(Dispatchers.IO)
            .collectResult(
                onSuccess = {
                    val currentSongId = state.song.id
                    val albumUiData = AlbumUiData.fromDomain(it)
                    val filteredTracks = albumUiData.tracks.filter { it.id != currentSongId }

                    updateState {
                        copy(album = albumUiData.copy(tracks = filteredTracks))
                    }
                },
                onFailure = {
                    Log.e("SongInfoViewModel", it.message ?: "Error")
                }
            )
    }
}