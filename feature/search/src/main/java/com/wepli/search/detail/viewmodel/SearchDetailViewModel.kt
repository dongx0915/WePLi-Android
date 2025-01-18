package com.wepli.search.detail.viewmodel

import base.BaseMviViewModel
import com.wepli.core.kotlin.suspendCollectResult
import com.wepli.search.detail.state.SearchDetailEffect
import com.wepli.search.detail.state.SearchDetailIntent
import com.wepli.search.detail.state.SearchDetailUiState
import com.wepli.uimodel.music.SongUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import repository.applemusic.AppleMusicRepository
import javax.inject.Inject

@HiltViewModel
class SearchDetailViewModel @Inject constructor(
    private val appleMusicRepository: AppleMusicRepository
) : BaseMviViewModel<SearchDetailUiState, SearchDetailEffect, SearchDetailIntent>(
    initialState = SearchDetailUiState()
) {
    override fun processIntent(intent: SearchDetailIntent) {
        when (intent) {
            is SearchDetailIntent.OnSearchQueryChanged -> handleSearchQueryChanged(intent.query)
            is SearchDetailIntent.RequestSearch -> searchMusic(intent.query)
        }
    }

    private fun handleSearchQueryChanged(query: String) = intent {
        reduce {
            state.copy(searchInput = query)
        }
    }

    private fun searchMusic(query: String) = intent {
        if (query.isEmpty()) return@intent

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
                    postSideEffect(SearchDetailEffect.SearchError(it.message ?: "알 수 없는 오류가 발생하였습니다."))
                }
            )
        }
    }
}