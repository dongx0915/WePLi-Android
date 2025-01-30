package com.wepli.data.post.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostBsideTrackRequestBody(
    @SerialName("post_id")
    val postId: Int,
    @SerialName("song_id")
    val songId: Int,
)