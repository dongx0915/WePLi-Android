package com.wepli.search.main.viewmodel

import base.BaseMviViewModel
import com.wepli.core.kotlin.suspendCollectResult
import com.wepli.search.main.mvi.SearchMainEffect
import com.wepli.search.main.mvi.SearchMainIntent
import com.wepli.search.main.mvi.SearchMainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import model.recommend.repository.KeywordRepository
import repository.applemusic.AppleMusicRepository
import javax.inject.Inject

@HiltViewModel
class SearchMainViewModel @Inject constructor(
    private val keywordRepository: KeywordRepository,
    private val appleMusicRepository: AppleMusicRepository,
) : BaseMviViewModel<SearchMainUiState, SearchMainEffect, SearchMainIntent>(
    initialState = SearchMainUiState()
) {

    init {
        getRecommendKeywords()
        getHotKeywords()
    }

    override fun processIntent(intent: SearchMainIntent) {
        // TODO: Implement
    }

    private fun getRecommendKeywords() = intent {
        launchWithHandler {
            keywordRepository.getRecommendKeyword()
                .flowOn(Dispatchers.IO)
                .suspendCollectResult(
                    onSuccess = {
                        reduce { state.copy(recommendKeyword = it.random()) }
                    }
                )
        }
    }

    private fun getHotKeywords() = intent {
        launchWithHandler {
            appleMusicRepository.getPopularSongs()
                .flowOn(Dispatchers.IO)
                .suspendCollectResult(
                    onSuccess = {
                        reduce {
                            state.copy(hotKeywords = it.map { song -> song.title })
                        }
                    }
                )
        }
    }
}