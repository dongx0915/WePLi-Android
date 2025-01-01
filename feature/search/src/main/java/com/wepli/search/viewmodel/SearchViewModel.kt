package com.wepli.search.viewmodel

import android.util.Log
import base.BaseMviViewModel
import com.wepli.core.kotlin.collectResult
import com.wepli.search.state.SearchEffect
import com.wepli.search.state.SearchIntent
import com.wepli.search.state.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import repository.applemusic.AppleMusicRepository
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val appleMusicRepository: AppleMusicRepository
) : BaseMviViewModel<SearchUiState, SearchEffect, SearchIntent>(
    initialState = SearchUiState()
) {
    override fun processIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.OnSearchQueryChanged -> handleSearchQueryChanged(intent.query)
            is SearchIntent.RequestSearch -> searchMusic(intent.query)
        }
    }

    private fun handleSearchQueryChanged(query: String) = intent {
        reduce {
            state.copy(searchInput = query)
        }
    }

    private fun searchMusic(query: String) = launchWithHandler {
        Log.d("SearchViewModel", "searchMusicQuery: $query")
        withContext(Dispatchers.IO) {
            appleMusicRepository.searchMusics(
                query.replace(" ", "+")
            )
        }.collectResult(
            onSuccess = { musics ->
                Log.d("SearchViewModel", "searchMusic: $musics")
            },
            onFailure = {
                Log.d("SearchViewModel", "searchMusic: ${it.message}")
            }
        )
    }
}