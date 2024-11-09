package com.wepli.community.main.state

import com.wepli.shared.feature.uimodel.community.PostUiData
import com.wepli.shared.feature.uimodel.user.UserUiData

data class CommunityMainState(
    val storyUsers: List<UserUiData> = emptyList(),
    val posts: List<PostUiData> = emptyList(),
)