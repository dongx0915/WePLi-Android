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
            SongInfoIntent.ToggleMusicVideo -> toggleMusicVideo()
        }
    }

    private fun init(song: SongUiData) = intent {
        updateState { copy(song = song) }

        launch {
            appleMusicRepository.getSongById(song.id)
                .suspendCollectResult(
                    onSuccess = {
                        getAlbumById(it.albumId.orEmpty())
                        getSimilarSongs(song.id, it.artistName, it.genres.first())
                        getAlbumByArtist(it.artistId.orEmpty())
                    },
                    onFailure = {
                        Log.e("SongInfoViewModel", it.message ?: "Error")
                    }
                )
        }
    }

    private fun getAlbumById(albumId: String) = intent {
        if (albumId.isEmpty()) return@intent

        appleMusicRepository.getAlbumById(albumId)
            .flowOn(Dispatchers.IO)
            .collectResult(
                onSuccess = {
                    updateState {
                        copy(album = AlbumUiData.fromDomain(it))
                    }
                },
                onFailure = {
                    Log.e("SongInfoViewModel", it.message ?: "Error")
                }
            )
    }

    private fun getSimilarSongs(currentSongId: String, artistName: String, genre: String) = intent {
        val searchQuery = "${artistName} ${genre}"

        appleMusicRepository.searchMusics(searchQuery, 10)
            .flowOn(Dispatchers.IO)
            .collectResult(
                onSuccess = {
                    val similarSongs = it.map { SongUiData.fromDomain(it) }
                        .filter { it.id != currentSongId }
                        .shuffled()
                        .take(5)

                    updateState { copy(similarSongs = similarSongs) }
                },
                onFailure = {
                    Log.e("SongInfoViewModel", it.message ?: "Error")
                }
            )
    }

    private fun getAlbumByArtist(artistId: String) = intent {
        if (artistId.isEmpty()) return@intent

        appleMusicRepository.getAlbumsByArtist(artistId)
            .flowOn(Dispatchers.IO)
            .collectResult(
                onSuccess = {
                    updateState {
                        copy(artistAlbums = it.map { AlbumUiData.fromDomain(it) })
                    }
                },
                onFailure = {
                    Log.e("SongInfoViewModel", it.message ?: "Error")
                }
            )
    }

    private fun toggleMusicVideo() = intent {
        updateState { copy(isMusicVideoExpanded = !isMusicVideoExpanded) }
    }
}