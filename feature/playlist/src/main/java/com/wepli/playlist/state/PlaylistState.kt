package com.wepli.playlist.state

import com.wepli.shared.feature.uimodel.playlist.PlaylistUiData

data class PlaylistState(
    val playlist: PlaylistUiData = PlaylistUiData(),
)