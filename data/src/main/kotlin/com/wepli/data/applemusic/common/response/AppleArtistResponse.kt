package com.wepli.data.applemusic.common.response

import kotlinx.serialization.Serializable
import model.artist.AppleArtist

/**
 * @property id 아티스트 id
 * @property type 타입 (항상 artists)
 * @property href 아티스트 조회 url
 */
@Serializable
data class AppleArtistResponse(
    val id: String? = null,
    val type: String? = null,
    val href: String? = null,
    val attributes: Attributes? = null,
) {
    /**
     * @property name 아티스트 이름
     * @property url 아티스트 url
     * @property genreNames 장르
     *
     */
    @Serializable
    data class Attributes(
        val name: String? = null,
        val url: String? = null,
        val genreNames: List<String>? = null,
        val artwork: AppleArtworkResponse? = null,
    )
}

fun AppleArtistResponse.toEntity(): AppleArtist {
    return AppleArtist(
        id = this.id.orEmpty(),
        href = this.href.orEmpty(),
        name = this.attributes?.name.orEmpty(),
        profileImg = this.attributes?.artwork?.url.orEmpty(),
    )
}