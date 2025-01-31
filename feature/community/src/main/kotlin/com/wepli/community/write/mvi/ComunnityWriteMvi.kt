package com.wepli.community.write.mvi

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.uimodel.music.SongUiData

data class CommunityWriteUiState(
    val title: FieldState = FieldState(),
    val contents: FieldState = FieldState(),
    val selectedSongs: List<SongUiData> = emptyList(),
    val isShowMusicSelectBottomSheet: Boolean = false
) : UiState {

    fun isPostEmpty(): Boolean {
        return title.text.isEmpty() || contents.text.isEmpty()
    }

    fun isPostHasError(): Boolean {
        return title.isLengthExceeded || contents.isLengthExceeded
    }

    data class FieldState(
        val text: String = "",
        val isLengthExceeded: Boolean = false
    )
}

interface CommunityWriteEffect : SideEffect {
    data object SuccessAddPost : CommunityWriteEffect
    data object FailedAddPost : CommunityWriteEffect
    data object ErrorPostIsEmpty : CommunityWriteEffect
    data object ErrorPostHasError : CommunityWriteEffect
}

interface CommunityWriteIntent : Intent {
    data class UpdateTitle(val title: String, val maxLength: Int) : CommunityWriteIntent
    data class UpdateContents(val contents: String, val maxLength: Int) : CommunityWriteIntent
    data class ShowMusicSelectBottomSheet(val isVisible: Boolean) : CommunityWriteIntent
    data class UpdateSelectedSongs(val selectedSongs: List<SongUiData>) : CommunityWriteIntent
    data class RemoveSelectedSongs(val song: SongUiData) : CommunityWriteIntent
    data object AddPost : CommunityWriteIntent
}