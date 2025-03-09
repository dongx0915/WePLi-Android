package model.relaylist

import common.DomainModel
import model.music.Song
import org.joda.time.DateTime
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * 릴레이리스트
 *
 * @property title 제목
 * @property description 설명
 * @property coverImgUrl 커버 이미지 URL
 * @property bSideTrack 현재 투표된 수록곡
 * @property songCount 곡 수
 * @property voteCount 총 투표 수
 * @property endDate 마감일
 */
data class Relaylist(
    val id: Int,
    val title: String,
    val description: String,
    val coverImgUrl: String,
    val bSideTrack: List<Song>,
    val songCount: Int,
    val voteCount: Int,
    val endDate: DateTime,
    val createdAt: DateTime,
    val artwork: Artwork? = null
) : DomainModel {

    data class Artwork(
        val backgroundColor: Long,
    )
}
