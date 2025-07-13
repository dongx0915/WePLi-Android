package com.wepli.shared.feature.uimodel.musicvideo

import com.wepli.shared.feature.common.UiModel
import com.wepli.shared.feature.common.UiModelMapper
import kotlinx.parcelize.Parcelize
import model.musicvideo.MusicVideo

@Parcelize
data class MusicVideoUiData(
    val id: String = "",
    val title: String = "",
    val channelTitle: String = "",
    val thumbnail: String = "",
) : UiModel {
    companion object : UiModelMapper<MusicVideo, MusicVideoUiData> {
        override fun fromDomain(domainModel: MusicVideo): MusicVideoUiData {
            return MusicVideoUiData(
                id = domainModel.id,
                title = domainModel.title,
                channelTitle = domainModel.channelTitle,
                thumbnail = domainModel.thumbnail
            )
        }
    }
}