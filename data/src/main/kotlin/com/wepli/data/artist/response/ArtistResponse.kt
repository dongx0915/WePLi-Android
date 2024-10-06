package com.wepli.data.artist.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.artist.Artist

@Serializable
data class ArtistListResponse(
    @SerialName("result")
    val artists: List<ArtistResponse>,
) {
    @Serializable
    data class ArtistResponse(
        @SerialName("name") val name: String,
        @SerialName("imageUrl") val profileUrl: String,
    )
}

fun ArtistListResponse.toDomain() = artists.map {
    Artist(
        name = it.name,
        profileUrl = it.profileUrl,
    )
}