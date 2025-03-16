package com.wepli.search.detail.viewmodel

import base.BaseMviViewModel
import com.wepli.core.kotlin.flow.suspendCollectResult
import com.wepli.search.detail.mvi.SearchDetailEffect
import com.wepli.search.detail.mvi.SearchDetailIntent
import com.wepli.search.detail.mvi.SearchDetailUiState
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
            is SearchDetailIntent.OnSongSelected -> handleSongSelected(intent.song)
            is SearchDetailIntent.OnCompleteSongSelect -> handleCompleteSongSelect()
            is SearchDetailIntent.SetMaxSelectCount -> handleSetMaxSelectCount(intent.count)
            is SearchDetailIntent.ShowSongInfoBottomSheet -> handleShowSongInfoBottomSheet(intent.selectedSong)
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
                appleMusicRepository.searchMusics(query)
            }.suspendCollectResult(
                onSuccess = { musics ->
                    reduce {
                        state.copy(
                            searchMusicResult = musics
                                .map(SongUiData::fromDomain)
                                .updateSelectionState(state.selectedSongs)
                        )
                    }
                },
                onFailure = {
                    postSideEffect(SearchDetailEffect.SearchError(it.message ?: "알 수 없는 오류가 발생하였습니다."))
                }
            )
        }
    }

    private fun handleSongSelected(song: SongUiData) = intent {
        reduce {
            val updatedSelectedSongs = LinkedHashSet(state.selectedSongs).apply {
                if (!add(song)) remove(song)
            }

            val updatedSearchMusicResult = state.searchMusicResult.map {
                if (it.id == song.id) it.copy(isSelected = !it.isSelected) else it
            }

            if (state.maxSelectCount < updatedSelectedSongs.size) {
                postSideEffect { SearchDetailEffect.SelectedLimitExceeded(state.maxSelectCount) }
                return@reduce state
            }

            state.copy(
                selectedSongs = updatedSelectedSongs,
                searchMusicResult = updatedSearchMusicResult
            )
        }
    }

    private fun List<SongUiData>.updateSelectionState(selectedSongs: Set<SongUiData>): List<SongUiData> {
        return this.map { song ->
            song.copy(isSelected = selectedSongs.contains(song))
        }
    }

    private fun handleCompleteSongSelect() = intent {
        postSideEffect(SearchDetailEffect.NavigateBackWithResult(state.selectedSongs.toList()))
    }

    private fun handleSetMaxSelectCount(count: Int) {
        updateState { copy(maxSelectCount = count) }
    }

    private fun handleShowSongInfoBottomSheet(selectedSong: SongUiData?) {
        updateState { copy(songInfo = selectedSong) }
    }
}