package com.wepli.shared.feature.uimodel.relaylist

import com.wepli.shared.feature.common.UiModel
import com.wepli.shared.feature.common.UiModelMapper
import com.wepli.uimodel.music.SongUiData
import kotlinx.parcelize.Parcelize
import model.relaylist.Relaylist
import org.joda.time.DateTime
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit


/**
 * 릴레이리스트
 *
 * @property title 제목
 * @property description 설명
 * @property coverImgUrl 커버 이미지 URL
 */
@Parcelize
data class RelaylistUiData(
    val id: Int,
    val title: String,
    val description: String,
    val coverImgUrl: String,
    val bSideTrack: List<SongUiData>,
    val songCnt: Int,
    val isLiked: Boolean,
    val endDate: DateTime,
    val createdAt: DateTime,
) : UiModel {

    constructor() : this(0, "", "", "", emptyList(), 0, false, DateTime.now(), DateTime.now())

    val formattedCreatedAt: String = createdAt.toString("yyyy.MM.dd")

    val remainingTime: Long
        get() {
            val now = DateTime.now()

            return endDate.minus(now.millis).millis
        }

    fun formatMilliseconds(ms: Long = remainingTime): String {
        val days = TimeUnit.MILLISECONDS.toDays(ms)
        val hours = TimeUnit.MILLISECONDS.toHours(ms) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(ms) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(ms) % 60

        return "${days}일 ${hours}시간 ${minutes}분 ${seconds}초"
    }

    companion object : UiModelMapper<Relaylist, RelaylistUiData> {
        override fun fromDomain(domainModel: Relaylist): RelaylistUiData {
            return RelaylistUiData(
                id = domainModel.id,
                title = domainModel.title,
                description = domainModel.description,
                coverImgUrl = domainModel.coverImgUrl,
                bSideTrack = domainModel.bSideTrack.map(SongUiData::fromDomain),
                songCnt = domainModel.songCount,
                isLiked = false,
                endDate = domainModel.endDate,
                createdAt = domainModel.createdAt,
            )
        }
    }
}