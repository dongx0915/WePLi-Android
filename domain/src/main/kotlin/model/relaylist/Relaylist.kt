package model.relaylist

import common.DomainModel

/**
 * 릴레이리스트
 *
 * @property title 제목
 * @property description 설명
 * @property coverImgUrl 커버 이미지 URL
 */
data class Relaylist(
    val title: String,
    val description: String,
    val coverImgUrl: String,
    val artwork: Artwork? = null
) : DomainModel {

    data class Artwork(
        val backgroundColor: Long,
    )
}
