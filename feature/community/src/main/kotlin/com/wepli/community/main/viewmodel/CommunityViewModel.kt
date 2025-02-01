package com.wepli.community.main.viewmodel

import android.util.Log
import base.BaseMviViewModel
import com.wepli.community.main.mvi.CommunityMainEffect
import com.wepli.community.main.mvi.CommunityMainIntent
import com.wepli.community.main.mvi.CommunityMainUiState
import com.wepli.core.kotlin.suspendCollectResult
import com.wepli.shared.feature.mock.userMockData
import com.wepli.shared.feature.uimodel.community.PostUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import repository.post.PostRepository
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val postRepository: PostRepository,
) : BaseMviViewModel<CommunityMainUiState, CommunityMainEffect, CommunityMainIntent>(
    initialState = CommunityMainUiState()
) {

    init {
        loadStoryUsers()
        loadPosts()
    }

    override fun processIntent(intent: CommunityMainIntent) {
        when (intent) {
            is CommunityMainIntent.LoadPosts -> loadPosts()
        }
    }

    private fun loadStoryUsers() {
        updateState { copy(storyUsers = userMockData) }
    }

    private fun loadPosts() = launchWithHandler {
        postRepository.getPosts()
            .flowOn(Dispatchers.IO)
            .suspendCollectResult(
                onSuccess = { posts ->
                    updateState {
                        copy(posts = posts.map { PostUiData.fromDomain(it) })
                    }
                },
                onFailure = {
                    postSideEffect { CommunityMainEffect.ErrorLoadPosts }
                }
            )
    }
}