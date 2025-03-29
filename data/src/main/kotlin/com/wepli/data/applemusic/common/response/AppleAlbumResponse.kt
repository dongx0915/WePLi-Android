package com.wepli.data.applemusic.common.response

import com.wepli.data.applemusic.common.response.base.AppleArtworkResponse
import com.wepli.data.applemusic.common.response.base.AppleEditorialNotes
import com.wepli.data.applemusic.common.response.base.AppleRelationshipsResponse
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
    val relationship: AppleRelationshipsResponse? = null,
) {
    /**
     * @property name 앨범 이름
     * @property url 앨범 url
     * @property genreNames 장르
     * @property artistName
     * @property artistUrl
     * @property audioVariants 오디오 변형 (dolby-atmos, dolby-audio, hi-res-lossless, lossless, lossy-stereo)
     * @property artwork
     * @property recordLabel
     * @property releaseDate 발매일
     * @property trackCount 앨범 수록곡 수
     * @property copyright 저작권
     * @property isSingle 싱글 여부
     * @property upc 앨범 제품 코드
     */
    @Serializable
    data class Attributes(
        val name: String? = null,
        val url: String? = null,
        val genreNames: List<String>? = null,
        val artistName: String? = null,
        val artistUrl: String? = null,
        val audioVariants: List<String>? = null,
        val recordLabel: String? = null,
        val releaseDate: String? = null,
        val trackCount: Int? = null,
        val copyright: String? = null,
        val isSingle: Boolean? = null,
        val upc: String? = null,
        val editorialNotes: AppleEditorialNotes? = null,
        val artwork: AppleArtworkResponse? = null,
        val relationship: AppleRelationshipsResponse? = null,
    )
}

fun AppleAlbumResponse.toEntity(): Album {
    val attr = this.attributes
    val artist = this.relationship?.artists?.data?.firstOrNull()
    val tracks = this.relationship?.tracks?.data

    return Album(
        id = this.id.orEmpty(),
        href = this.href.orEmpty(),
        name = attr?.name.orEmpty(),
        description = attr?.editorialNotes?.standard.orEmpty(),
        coverImg = attr?.artwork?.url.orEmpty(),
        albumUrl = attr?.url.orEmpty(),
        isSingle = attr?.isSingle ?: false,
        artistId = artist?.id.orEmpty(),
        artistName = attr?.artistName.orEmpty(),
        releaseDate = attr?.releaseDate.orEmpty(),
        genres = attr?.genreNames.orEmpty(),
        trackCount = attr?.trackCount ?: 0,
        tracks = tracks?.map { it.toEntity() }.orEmpty(),
    )
}