package com.wepli.data.playlist.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.playlist.RecommendPlaylist

@Serializable
data class RecommendPlaylistResponse(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("cover_img")
    val coverImgUrl: String? = null,
)

fun List<RecommendPlaylistResponse>.toEntities(): List<RecommendPlaylist> {
    return map { it.toEntity() }
}

fun RecommendPlaylistResponse.toEntity(): RecommendPlaylist {
    return RecommendPlaylist(
        id = id ?: -1,
        title = title.orEmpty(),
        coverImgUrl = coverImgUrl.orEmpty()
    )
}