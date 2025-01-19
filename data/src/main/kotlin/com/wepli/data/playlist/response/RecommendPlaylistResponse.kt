package com.wepli.data.playlist.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.playlist.RecommendPlaylist

@Serializable
data class RecommendPlaylistResponse(
    @SerialName("title")
    val title: String?,
    @SerialName("imageUrl")
    val coverImgUrl: String?,
)

fun List<RecommendPlaylistResponse>.toEntities(): List<RecommendPlaylist> {
    return map { it.toEntity() }
}

fun RecommendPlaylistResponse.toEntity(): RecommendPlaylist {
    return RecommendPlaylist(
        title = title.orEmpty(),
        coverImgUrl = coverImgUrl.orEmpty()
    )
}