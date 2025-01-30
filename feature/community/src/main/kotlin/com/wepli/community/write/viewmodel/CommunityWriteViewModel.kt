package com.wepli.community.write.viewmodel

import base.BaseMviViewModel
import com.wepli.community.write.mvi.CommunityWriteEffect
import com.wepli.community.write.mvi.CommunityWriteIntent
import com.wepli.community.write.mvi.CommunityWriteUiState
import com.wepli.uimodel.music.SongUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommunityWriteViewModel @Inject constructor() : BaseMviViewModel<CommunityWriteUiState, CommunityWriteEffect, CommunityWriteIntent>(
    initialState = CommunityWriteUiState()
) {

    override fun processIntent(intent: CommunityWriteIntent) {
        when (intent) {
            is CommunityWriteIntent.UpdateTitle -> {
                handleUpdateTitle(intent.title, intent.maxLength)
            }
            is CommunityWriteIntent.UpdateContents -> {
                handleUpdateContents(intent.contents, intent.maxLength)
            }
            is CommunityWriteIntent.ShowMusicSelectBottomSheet -> {
                handleShowMusicSelectBottomSheet(intent.isVisible)
            }
            is CommunityWriteIntent.UpdateSelectedSongs -> {
                handleUpdateSelectedSongs(intent.selectedSongs)
            }
            is CommunityWriteIntent.RemoveSelectedSongs -> {
                handleRemoveSelectedSongs(intent.song)
            }
        }
    }

    private fun handleUpdateTitle(title: String, maxLength: Int) {
        val updatedFiledState = CommunityWriteUiState.FieldState(
            text = title,
            isLengthExceeded = title.length > maxLength
        )
        updateState { copy(title = updatedFiledState) }
    }

    private fun handleUpdateContents(contents: String, maxLength: Int) {
        val updatedFiledState = CommunityWriteUiState.FieldState(
            text = contents,
            isLengthExceeded = contents.length > maxLength
        )

        updateState { copy(contents = updatedFiledState) }
    }

    private fun handleShowMusicSelectBottomSheet(isVisible: Boolean) {
        updateState { copy(isShowMusicSelectBottomSheet = isVisible) }
    }

    private fun handleUpdateSelectedSongs(newSelectedSongs: List<SongUiData>) {
        updateState {
            val updatedSelectSongs: List<SongUiData> = (selectedSongs + newSelectedSongs).distinct()
            copy(selectedSongs = updatedSelectSongs)
        }
    }

    private fun handleRemoveSelectedSongs(song: SongUiData) {
        updateState { copy(selectedSongs = selectedSongs - song) }
    }
}