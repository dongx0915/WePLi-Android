package model.artist

import common.DomainModel

/**
 * 가수 정보
 * @property name 가수 이름
 * @property profileUrl 가수 프로필 URL
 */
data class Artist(
    val name: String,
    val profileUrl: String,
) : DomainModel

data class AppleArtist(
    val id: String,
    val href: String,
    val name: String,
    val profileImg: String,
) : DomainModel