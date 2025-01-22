package com.wepli.shared.feature.uimodel.community

import com.wepli.shared.feature.common.UiModel
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class CommentUiData(
    val nickname: String,
    val profileImg: String,
    val content: String,
    val likeCount: Int,
    val replyCount: Int,
    val createdAt: Date,
) : UiModel