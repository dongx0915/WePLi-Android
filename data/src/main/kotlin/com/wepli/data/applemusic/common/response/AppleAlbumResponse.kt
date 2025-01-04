package com.wepli.data.applemusic.common.response

import kotlinx.serialization.Serializable
import model.album.Album

/**
 * @property id 앨범 id
 * @property type 타입 (항상 albums)
 * @property href 앨범 조회 url
 */
@Serializable
data class AppleAlbumResponse(
    val id: String? = null,
    val type: String? = null,
    val href: String? = null,
    val attributes: Attributes? = null,
) {
    /**
     * @property name 앨범 이름
     * @property url 앨범 url
     * @property genreNames 장르
     * @property artistName
     * @property artistUrl
     * @property artwork
     * @property recordLabel
     * @property releaseDate
     * @property trackCount 앨범 수록곡 수
     * @property copyright 저작권
     */
    @Serializable
    data class Attributes(
        val name: String? = null,
        val url: String? = null,
        val genreNames: List<String>? = null,
        val artistName: String? = null,
        val artistUrl: String? = null,
        val editorialNotes: Notes? = null,
        val artwork: AppleArtworkResponse? = null,
        val recordLabel: String? = null,
        val releaseDate: String? = null,
        val trackCount: Int? = null,
        val copyright: String? = null,
    ) {
        @Serializable
        data class Notes(
            val standard: String? = null,
        )
    }
}

fun AppleAlbumResponse.toEntity(): Album {
    return Album(
        id = this.id.orEmpty(),
        href = this.href.orEmpty(),
        name = this.attributes?.name.orEmpty(),
        artistName = this.attributes?.artistName.orEmpty(),
        genres = this.attributes?.genreNames.orEmpty(),
        releaseDate = this.attributes?.releaseDate.orEmpty(),
        coverImg = this.attributes?.artwork?.url.orEmpty(),
        trackCount = this.attributes?.trackCount ?: 0,
        description = this.attributes?.editorialNotes?.standard.orEmpty(),
    )
}