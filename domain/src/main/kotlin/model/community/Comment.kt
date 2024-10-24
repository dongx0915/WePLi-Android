package model.community

import common.DomainModel
import java.util.Date

data class Comment(
    val nickname: String,
    val profileImg: String,
    val content: String,
    val likeCount: Int,
    val replyCount: Int,
    val createdAt: Date,
) : DomainModel