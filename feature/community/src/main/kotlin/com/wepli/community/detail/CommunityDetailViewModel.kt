package com.wepli.community.detail

import base.BaseMviViewModel
import base.BaseViewModel
import base.Intent
import base.SideEffect
import com.wepli.community.detail.state.CommunityDetailEffect
import com.wepli.community.detail.state.CommunityDetailIntent
import com.wepli.community.detail.state.CommunityDetailState
import com.wepli.shared.feature.mock.commentMockData
import com.wepli.shared.feature.uimodel.community.PostUiData
import com.wepli.shared.feature.uimodel.user.UserUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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