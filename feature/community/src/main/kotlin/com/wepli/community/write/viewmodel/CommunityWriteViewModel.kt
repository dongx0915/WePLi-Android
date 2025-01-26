package com.wepli.community.write.viewmodel

import base.BaseMviViewModel
import com.wepli.community.write.mvi.CommunityWriteEffect
import com.wepli.community.write.mvi.CommunityWriteIntent
import com.wepli.community.write.mvi.CommunityWriteUiState
import com.wepli.shared.feature.mock.songMockData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommunityWriteViewModel @Inject constructor() : BaseMviViewModel<CommunityWriteUiState, CommunityWriteEffect, CommunityWriteIntent>(
    initialState = CommunityWriteUiState()
) {
    init {
        intent {
            reduce { state.copy(selectedSongs = songMockData.take(10)) }
        }
    }

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
        }
    }

    private fun handleUpdateTitle(title: String, maxLength: Int) = intent {
        reduce {
            state.copy(
                title = CommunityWriteUiState.FieldState(
                    text = title,
                    isLengthExceeded = title.length > maxLength
                ),
            )
        }
    }

    private fun handleUpdateContents(contents: String, maxLength: Int) = intent {
        reduce {
            state.copy(
                contents = CommunityWriteUiState.FieldState(
                    text = contents,
                    isLengthExceeded = contents.length > maxLength
                ),
            )
        }
    }

    private fun handleShowMusicSelectBottomSheet(isVisible: Boolean) = intent {
        reduce {
            state.copy(
                isShowMusicSelectBottomSheet = isVisible
            )
        }
    }
}