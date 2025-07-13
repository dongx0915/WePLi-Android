package com.wepli.feature.song.info.mvi

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.shared.feature.uimodel.album.AlbumUiData
import com.wepli.uimodel.music.SongUiData
import model.album.Album

data class SongInfoUiState(
    val song: SongUiData = SongUiData(),
    val artistAlbums: List<AlbumUiData> = emptyList(),
    val album: AlbumUiData = AlbumUiData(),
    val similarSongs: List<SongUiData> = emptyList(),
    val isMusicVideoExpanded: Boolean = false
) : UiState

interface SongInfoEffect : SideEffect

interface SongInfoIntent : Intent {
    data class Init(val song: SongUiData) : SongInfoIntent
    object ToggleMusicVideo : SongInfoIntent
}