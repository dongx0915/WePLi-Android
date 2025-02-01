package com.wepli.community.main.mvi

import base.Intent
import base.SideEffect
import base.UiState
import com.wepli.shared.feature.uimodel.community.PostUiData
import com.wepli.shared.feature.uimodel.user.UserUiData

data class CommunityMainUiState(
    val storyUsers: List<UserUiData> = emptyList(),
    val posts: List<PostUiData> = emptyList(),
) : UiState

interface CommunityMainEffect : SideEffect {
    data object ErrorLoadPosts : CommunityMainEffect
}

interface CommunityMainIntent : Intent {
    object LoadPosts : CommunityMainIntent
}