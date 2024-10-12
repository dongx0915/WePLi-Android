package com.wepli.uimodel.community

import java.util.Date

data class CommentUiData(
    val nickname: String,
    val profileImg: String,
    val content: String,
    val likeCount: Int,
    val replyCount: Int,
    val createdAt: Date,
)