package com.wepli.data.applemusic.common.response

import com.wepli.data.applemusic.common.response.base.AppleArtworkResponse
import kotlinx.serialization.Serializable
import model.music.Song
import org.joda.time.LocalDate

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
        val relationship: AppleRelationshipsResponse? = null,

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
    val attr: AppleSongResponse.Attributes? = this.attributes
    val relationship: AppleRelationshipsResponse? = this.attributes?.relationship
    val album = relationship?.albums?.data?.firstOrNull()
    val artist = relationship?.artists?.data?.firstOrNull()

    return Song(
        id = this.id.orEmpty(),
        title = attr?.name.orEmpty(),
        artistName = attr?.artistName.orEmpty(),
        artistId = artist?.id.orEmpty(),
        albumName = attr?.albumName.orEmpty(),
        albumId = album?.id.orEmpty(),
        coverImg = attr?.artwork?.url.orEmpty(),
        composers = attr?.composerName
            ?.takeIf { it.isNotBlank() }
            ?.split(", ")
            .orEmpty(),
        genres = attr?.genreNames.orEmpty(),
        url = attr?.url.orEmpty(),
        previewMusicUrl = attr?.previews?.map { it.url.orEmpty() }.orEmpty(),
        releaseDate = attr?.releaseDate?.let { LocalDate(it) } ?: LocalDate.now(),
        durationMillis = attr?.durationInMillis?.toLong() ?: 0L,
        playParams = Song.PlayParams(
            id = attr?.playParams?.id.orEmpty(),
            kind = attr?.playParams?.kind.orEmpty(),
        ),
        isrc = attr?.isrc.orEmpty(),
        href = this.href.orEmpty(),
    )
}