package com.wepli.feature.song.info

import base.BaseMviViewModel
import com.wepli.feature.song.info.mvi.SongInfoEffect
import com.wepli.feature.song.info.mvi.SongInfoIntent
import com.wepli.feature.song.info.mvi.SongInfoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SongInfoViewModel @Inject constructor() : BaseMviViewModel<SongInfoUiState, SongInfoEffect, SongInfoIntent>(
    initialState = SongInfoUiState()
) {

    override fun processIntent(intent: SongInfoIntent) {
        when (intent) {
            is SongInfoIntent.Init -> {
                updateState { copy(song = intent.song) }
            }
        }
    }
}