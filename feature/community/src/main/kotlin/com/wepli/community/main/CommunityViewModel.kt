package com.wepli.community.main

import android.util.Log
import base.BaseViewModel
import com.wepli.community.main.state.CommunityMainState
import com.wepli.core.kotlin.suspendCollectResult
import com.wepli.shared.feature.mock.userMockData
import com.wepli.shared.feature.uimodel.community.PostUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import repository.post.PostRepository
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val postRepository: PostRepository,
) : BaseViewModel() {

    private val _state = MutableStateFlow(CommunityMainState())
    val state: StateFlow<CommunityMainState> = _state.asStateFlow()

    init {
        loadStoryUsers()
        loadPosts()
    }

    private fun loadStoryUsers() = launchWithHandler {
        _state.update {
            it.copy(storyUsers = userMockData)
        }
    }

    private fun loadPosts() = launchWithHandler {
        postRepository.getPosts()
            .flowOn(Dispatchers.IO)
            .suspendCollectResult(
                onSuccess = { posts ->
                    Log.d("CommunityViewModel", "loadPosts: $posts")
                    _state.update {
                        it.copy(posts = posts.map { PostUiData.fromDomain(it) })
                    }
                },
                onFailure = {
                    Log.e("CommunityViewModel", "loadPosts: $it")
                }
            )
    }
}