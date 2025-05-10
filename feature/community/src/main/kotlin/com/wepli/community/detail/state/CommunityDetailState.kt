package com.wepli.community.detail.state

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.shared.feature.uimodel.community.CommentUiData
import com.wepli.shared.feature.uimodel.community.PostUiData
import com.wepli.shared.feature.uimodel.user.UserUiData

data class CommunityDetailState(
    val post: PostUiData = PostUiData(),
    val user: UserUiData = UserUiData(),
    val comments: List<CommentUiData> = emptyList(),

    // TextField
    val comment: String = "",
): UiState

sealed interface CommunityDetailEffect : SideEffect {
}

sealed interface CommunityDetailIntent : Intent {
    data class InitPost(val post: PostUiData) : CommunityDetailIntent
    data class OnChangedComment(val comment: String) : CommunityDetailIntent
}