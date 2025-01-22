package com.wepli.playlist.mvi

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.shared.feature.uimodel.playlist.PlaylistUiData

data class PlaylistUiState(
    val playlist: PlaylistUiData = PlaylistUiData(),
) : UiState

interface PlaylistEffect : SideEffect {
    object PlaylistFetchError : PlaylistEffect
}

interface PlaylistIntent : Intent {
    object OnClickLike : PlaylistIntent
    data class RequestPlaylist(val playlistId: Int) : PlaylistIntent
}