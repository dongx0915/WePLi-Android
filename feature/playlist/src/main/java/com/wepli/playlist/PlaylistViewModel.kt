package com.wepli.playlist

import base.BaseMviViewModel
import com.wepli.core.kotlin.suspendCollectResult
import com.wepli.playlist.mvi.PlaylistEffect
import com.wepli.playlist.mvi.PlaylistIntent
import com.wepli.playlist.mvi.PlaylistUiState
import com.wepli.shared.feature.uimodel.playlist.PlaylistUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import repository.playlist.PlaylistRepository
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextInt

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val playlistRepository: PlaylistRepository,
) : BaseMviViewModel<PlaylistUiState, PlaylistEffect, PlaylistIntent>(
    initialState = PlaylistUiState()
) {

    init {
        getPlaylistById(Random.nextInt(0..9))
    }

    override fun processIntent(intent: PlaylistIntent) {
        when (intent) {
            PlaylistIntent.OnClickLike -> toggleLiked()
        }
    }

    private fun toggleLiked() = intent {
        val likeState = state.playlist.isLiked
        reduce {
            state.copy(
                playlist = state.playlist.copy(
                    isLiked = !likeState
                )
            )
        }
    }

    private fun getPlaylistById(id: Int) = intent {
        launchWithHandler {
            playlistRepository.getPlaylistById(id)
                .suspendCollectResult(
                    onSuccess = {
                        reduce {
                            state.copy(playlist = PlaylistUiData.fromDomain(it))
                        }
                    }
                )
        }
    }
}