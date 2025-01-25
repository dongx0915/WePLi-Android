package com.wepli.community.write.viewmodel

import base.BaseMviViewModel
import com.wepli.community.write.mvi.CommunityWriteEffect
import com.wepli.community.write.mvi.CommunityWriteIntent
import com.wepli.community.write.mvi.CommunityWriteUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommunityWriteViewModel @Inject constructor() : BaseMviViewModel<CommunityWriteUiState, CommunityWriteEffect, CommunityWriteIntent>(
    initialState = CommunityWriteUiState()
) {

    override fun processIntent(intent: CommunityWriteIntent) {
        when (intent) {
            is CommunityWriteIntent.UpdateTitle -> {
                handleUpdateTitle(intent.title)
            }
            is CommunityWriteIntent.UpdateContents -> {
                handleUpdateContents(intent.contents)
            }
        }
    }

    private fun handleUpdateTitle(title: String) = intent {
        reduce { state.copy(title = title) }
    }

    private fun handleUpdateContents(contents: String) = intent {
        reduce { state.copy(contents = contents) }
    }
}