package com.wepli.feature.song.info.mvi

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.uimodel.music.SongUiData

data class SongInfoUiState(
    val song: SongUiData = SongUiData()
) : UiState

interface SongInfoEffect : SideEffect

interface SongInfoIntent : Intent {
    data class Init(val song: SongUiData) : SongInfoIntent
}