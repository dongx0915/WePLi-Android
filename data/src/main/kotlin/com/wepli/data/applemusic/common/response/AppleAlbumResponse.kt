package com.wepli.data.applemusic.common.response

import model.album.Album

/**
 * @property id 앨범 id
 * @property type 타입 (항상 albums)
 * @property href 앨범 조회 url
 */
data class AppleAlbumResponse(
    val id: String?,
    val type: String?,
    val href: String?,
    val attributes: Attributes?,
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
    data class Attributes(
        val name: String?,
        val url: String?,
        val genreNames: List<String>?,
        val artistName: String?,
        val artistUrl: String?,
        val editorialNotes: Notes?,
        val artwork: AppleArtworkResponse?,
        val recordLabel: String?,
        val releaseDate: String?,
        val trackCount: Int?,
        val copyright: String?,
    ) {
        data class Notes(
            val standard: String,
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