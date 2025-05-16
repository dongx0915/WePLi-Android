package com.wepli.community.detail

import base.BaseMviViewModel
import com.wepli.community.detail.state.CommunityDetailEffect
import com.wepli.community.detail.state.CommunityDetailIntent
import com.wepli.community.detail.state.CommunityDetailState
import com.wepli.shared.feature.mock.commentMockData
import com.wepli.shared.feature.uimodel.user.UserUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import repository.user.UserRepository
import javax.inject.Inject

@HiltViewModel
class CommunityDetailViewModel @Inject constructor(
    private val userRepository: UserRepository,
): BaseMviViewModel<CommunityDetailState, CommunityDetailEffect, CommunityDetailIntent>(
    initialState = CommunityDetailState()
) {

    init {
        loadUser()
        loadComment()
    }

    override fun processIntent(intent: CommunityDetailIntent) {
        when (intent) {
            is CommunityDetailIntent.InitPost -> {
                updateState { copy(post = intent.post) }
            }

            is CommunityDetailIntent.OnChangedComment -> {
                updateState { copy(comment = intent.comment) }
            }

            CommunityDetailIntent.UploadComment -> {
                // TODO : 댓글 업로드
            }
        }
    }

    private fun loadUser() = launch {
        userRepository.getUser()?.let {
            updateState { copy(user = UserUiData.fromDomain(it)) }
        }
    }

    private fun loadComment() {
        updateState {
            copy(comments = commentMockData().sortedByDescending { it.createdAt })
        }
    }
}