package com.wepli.search.main.viewmodel

import base.BaseMviViewModel
import com.wepli.search.main.mvi.SearchMainEffect
import com.wepli.search.main.mvi.SearchMainIntent
import com.wepli.search.main.mvi.SearchMainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import repository.applemusic.AppleMusicRepository
import javax.inject.Inject

@HiltViewModel
class SearchMainViewModel @Inject constructor(
    private val appleMusicRepository: AppleMusicRepository,
): BaseMviViewModel<SearchMainUiState, SearchMainEffect, SearchMainIntent>(
    initialState = SearchMainUiState()
) {

    override fun processIntent(intent: SearchMainIntent) {
        // TODO: Implement
    }

}