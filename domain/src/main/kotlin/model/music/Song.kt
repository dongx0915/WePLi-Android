package model.music

import common.DomainModel

/**
 * 음악 정보
 *
 * @property title 제목
 * @property artist 아티스트
 * @property albumCoverImg 앨범 커버 이미지 URL
 */
data class Song(
    val title: String,
    val artist: String,
    val album: String,
    val albumCoverImg: String,
) : DomainModel

data class AppleSong(
    val id: String,
    val href: String,
    val title: String,
    val albumName: String,
    val artistName: String,
    val genres: List<String>,
    val durationMillis: Long,
    val coverImg: String,
    // artwork
) : DomainModel