package com.wepli.community.detail.state

import com.wepli.uimodel.community.CommentUiData
import com.wepli.uimodel.community.PostUiData

data class CommunityDetailState(
    val post: PostUiData = PostUiData(),
    val comments: List<CommentUiData> = emptyList(),
)