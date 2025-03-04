package com.wepli.shared.feature.uimodel.relaylist

import android.icu.text.DateFormat
import com.wepli.shared.feature.common.UiModel
import com.wepli.shared.feature.common.UiModelMapper
import com.wepli.uimodel.music.SongUiData
import kotlinx.parcelize.Parcelize
import model.relaylist.Relaylist
import java.util.Date


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
    val endDate: Date,
    val isLiked: Boolean,
) : UiModel {

    companion object : UiModelMapper<Relaylist, RelaylistUiData> {
        override fun fromDomain(domainModel: Relaylist): RelaylistUiData {
            return RelaylistUiData(
                id = domainModel.id,
                title = domainModel.title,
                description = domainModel.description,
                coverImgUrl = domainModel.coverImgUrl,
                bSideTrack = domainModel.bSideTrack.map(SongUiData::fromDomain),
                songCnt = domainModel.songCount,
                endDate = DateFormat.getInstance().parse(domainModel.endDate),
                isLiked = false,
            )
        }
    }
}