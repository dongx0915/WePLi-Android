package com.wepli.feature.namecard.detail.mvi

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.uimodel.music.SongUiData

data class NameCardDetailUiState(
    val selectedFavoriteSong: SongUiData? = null
) : UiState

interface NameCardDetailEffect : SideEffect {

}

interface NameCardDetailIntent : Intent {
    data class OnFavoriteSongSelected(val song: SongUiData) : NameCardDetailIntent
}