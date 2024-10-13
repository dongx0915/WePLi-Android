package com.wepli.uimodel.music

import com.wepli.common.UiModel
import com.wepli.common.UiModelMapper
import kotlinx.parcelize.Parcelize
import model.music.ChartMusic

/**
 * 차트 음악 정보
 *
 * @property rank 순위
 * @property title 제목
 * @property artist 아티스트
 * @property album 앨범
 * @property albumCoverUrl 앨범 커버 URL
 */
@Parcelize
data class ChartMusicUiData(
    val rank: Int,
    val title: String,
    val artist: String,
    val album: String,
    val albumCoverUrl: String,
) : UiModel {

    constructor() : this(0, "", "", "", "")

    companion object : UiModelMapper<ChartMusic, ChartMusicUiData> {

        override fun fromDomain(domainModel: ChartMusic): ChartMusicUiData {
            return ChartMusicUiData(
                rank = domainModel.rank,
                title = domainModel.title,
                artist = domainModel.artist,
                album = domainModel.album,
                albumCoverUrl = domainModel.albumCoverUrl
            )
        }
    }
}
