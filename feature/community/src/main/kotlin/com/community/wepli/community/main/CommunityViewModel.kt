package com.community.wepli.community.main

import base.BaseViewModel
import com.community.wepli.community.main.state.CommunityMainState
import com.community.wepli.community.mock.postMockData
import com.community.wepli.community.mock.userMockData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor() : BaseViewModel() {

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
        val posts = List(100) { postMockData }.flatten().shuffled()

        _state.update {
            it.copy(posts = posts)
        }
    }
}