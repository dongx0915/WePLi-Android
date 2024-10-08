package com.wepli.data.playlist.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.playlist.RecommendPlaylist

@Serializable
data class PlaylistResponse(
    @SerialName("title")
    val title: String?,
    @SerialName("imageUrl")
    val coverImgUrl: String?,
)

fun List<PlaylistResponse>.toEntities(): List<RecommendPlaylist> {
    return map { it.toEntity() }
}

fun PlaylistResponse.toEntity(): RecommendPlaylist {
    return RecommendPlaylist(
        title = title.orEmpty(),
        coverImgUrl = coverImgUrl.orEmpty()
    )
}