package com.wepli.search.viewmodel

import base.BaseMviViewModel
import com.wepli.core.kotlin.suspendCollectResult
import com.wepli.search.state.SearchEffect
import com.wepli.search.state.SearchIntent
import com.wepli.search.state.SearchUiState
import com.wepli.uimodel.music.SongUiData
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

    private fun searchMusic(query: String) = intent {
        launchWithHandler {
            withContext(Dispatchers.IO) {
                appleMusicRepository.searchMusics(
                    query.replace(" ", "+")
                )
            }.suspendCollectResult(
                onSuccess = { musics ->
                    reduce {
                        state.copy(searchMusicResult = musics.map(SongUiData::fromDomain))
                    }
                },
                onFailure = {
                    postSideEffect(SearchEffect.SearchError(it.message ?: "알 수 없는 오류가 발생하였습니다."))
                }
            )
        }
    }
}