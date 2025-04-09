package model.music

import common.DomainModel
import org.joda.time.LocalDate

/**
 * 음악 정보
 *
 * @property title 제목
 * @property artistName 아티스트
 * @property artistId 아티스트 ID (Optional)
 * @property albumName 앨범 이름
 * @property albumId 앨범 ID (Optional)
 * @property coverImg 앨범 커버 이미지 URL
 * @property composers 작곡가
 * @property genres 장르
 * @property url 노래 페이지 URL
 * @property previewMusicUrl 미리듣기 URL
 */
data class Song(
    val id: String,
    val title: String,
    val artistName: String,
    val artistId: String? = null,
    val albumName: String,
    val albumId: String? = null,
    val coverImg: String,
    val composers: List<String>,
    val genres: List<String>,
    val url: String,
    val previewMusicUrl: List<String>,
    val releaseDate: LocalDate,
    val isrc: String,
    val durationMillis: Long,
    val playParams: PlayParams,
    val href: String,
    // artwork
) : DomainModel {

    data class PlayParams(
        val id: String,
        val kind: String,
    )
}