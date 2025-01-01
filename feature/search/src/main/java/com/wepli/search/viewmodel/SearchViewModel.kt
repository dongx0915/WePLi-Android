package com.wepli.search.viewmodel

import base.BaseMviViewModel
import com.wepli.search.state.SearchEffect
import com.wepli.search.state.SearchIntent
import com.wepli.search.state.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor() : BaseMviViewModel<SearchUiState, SearchEffect, SearchIntent>(
    initialState = SearchUiState()
) {
    override fun processIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.OnSearchQueryChanged -> handleSearchQueryChanged(intent.query)
        }
    }

    private fun handleSearchQueryChanged(query: String) = intent {
        reduce {
            state.copy(searchInput = query)
        }
    }
}