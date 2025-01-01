package model.music

import common.DomainModel

/**
 * 음악 정보
 *
 * @property title 제목
 * @property artistName 아티스트
 * @property coverImg 앨범 커버 이미지 URL
 */
data class Song(
    val id: String,
    val title: String,
    val artistName: String,
    val albumName: String,
    val coverImg: String,
    val href: String,
    val genres: List<String>,
    val durationMillis: Long,
    // artwork
) : DomainModel