package com.wepli.data.applemusic.common.response

import kotlinx.serialization.Serializable
import model.music.Song

/**
 * @property id 노래 id
 * @property type 타입 (항상 songs)
 * @property href 노래 조회 url
 * @property attributes 노래 속성
 */
@Serializable
data class AppleSongResponse(
    val id: String? = null,
    val type: String? = null,
    val href: String? = null,
    val attributes: Attributes? = null,
) {
    /**
     * @property name 노래 제목
     * @property albumName 앨범 이름
     * @property artistName 가수 이름
     * @property hasLyrics 가사 여부
     * @property durationInMillis 노래 재생 시간
     * @property genreNames 장르
     * @property url 노래 페이지 URL
     * @property playParams 플레이 파라미터 (노래 재생에 사용, 미리듣기 - 사용 가능, 풀버전 - 애플 뮤직 구독 필요)
     * @property previews 미리듣기 m4a 파일 url
     * @property releaseDate 출시일
     * @property trackNumber 앨범 내에서의 트랙 순서
     * @property artwork 노래 이미지 정보
     */
    @Serializable
    data class Attributes(
        val name: String? = null,
        val albumName: String? = null,
        val artistName: String? = null,
        val hasLyrics: Boolean? = null,
        val durationInMillis: Int? = null,
        val genreNames: List<String>? = null,
        val url: String? = null,
        val playParams: PlayParams? = null,
        val previews: List<Preview>? = null,
        val releaseDate: String? = null,
        val trackNumber: Int? = null,
        val artwork: AppleArtworkResponse? = null,

        /* 필요 없을 것 같은 값들 */
        val composerName: String? = null,
        val discNumber: Int? = null,
        val isAppleDigitalMaster: Boolean? = null,
        val isrc: String? = null,
    ) {
        @Serializable
        data class Preview(
            val url: String? = null
        )

        @Serializable
        data class PlayParams(
            val id: String? = null,
            val kind: String? = null
        )
    }
}

fun AppleSongResponse.toEntity(): Song {
    return Song(
        id = this.id.orEmpty(),
        href = this.href.orEmpty(),
        title = this.attributes?.name.orEmpty(),
        albumName = this.attributes?.albumName.orEmpty(),
        artistName = this.attributes?.artistName.orEmpty(),
        genres = this.attributes?.genreNames.orEmpty(),
        durationMillis = this.attributes?.durationInMillis?.toLong() ?: 0L,
        coverImg = this.attributes?.artwork?.url.orEmpty(),
    )
}