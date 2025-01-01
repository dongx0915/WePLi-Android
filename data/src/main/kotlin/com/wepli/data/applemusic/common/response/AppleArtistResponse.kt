package com.wepli.data.applemusic.common.response

import model.artist.AppleArtist

/**
 * @property id 아티스트 id
 * @property type 타입 (항상 artists)
 * @property href 아티스트 조회 url
 */
data class AppleArtistResponse(
    val id: String?,
    val type: String?,
    val href: String?,
    val attributes: Attributes?,
) {
    /**
     * @property name 아티스트 이름
     * @property url 아티스트 url
     * @property genreNames 장르
     *
     */
    data class Attributes(
        val name: String?,
        val url: String?,
        val genreNames: List<String>?,
        val artwork: AppleArtworkResponse?,
    )
}

fun AppleArtistResponse.toEntity(): AppleArtist {
    return AppleArtist(
        id = this.id.orEmpty(),
        href = this.href.orEmpty(),
        name = this.attributes.name.orEmpty(),
        profileImg = this.attributes?.artwork?.url.orEmpty(),
    )
}