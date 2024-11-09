package com.wepli.community.detail

import base.BaseViewModel
import com.wepli.community.detail.state.CommunityDetailState
import com.wepli.shared.feature.mock.commentMockData
import com.wepli.shared.feature.uimodel.community.PostUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CommunityDetailViewModel @Inject constructor(): BaseViewModel() {

    private val _state = MutableStateFlow(CommunityDetailState())
    val state: StateFlow<CommunityDetailState> = _state.asStateFlow()

    init {
        loadComment()
    }

    fun setPost(postUiData: PostUiData) {
        _state.update {
            it.copy(post = postUiData)
        }
    }

    private fun loadComment() {
        _state.update {
            it.copy(comments = commentMockData().sortedByDescending { it.createdAt })
        }
    }
}