package com.wepli.shared.feature.uimodel.musicvideo

import com.wepli.shared.feature.common.UiModel
import com.wepli.shared.feature.common.UiModelMapper
import kotlinx.parcelize.Parcelize
import model.musicvideo.MusicVideo

@Parcelize
data class MusicVideoUiData(
    val id: String = "",
    val title: String = "",
    val playtime: Float = 0f,
    val thumbnail: String = "",
) : UiModel {

    val formattedPlaytime: String
        get() = formatDuration(playtime)

    private fun formatDuration(seconds: Float): String {
        val totalSeconds = seconds.toInt()
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val remainingSeconds = totalSeconds % 60

        return if (hours > 0) {
            String.format("%d:%02d:%02d", hours, minutes, remainingSeconds)
        } else {
            String.format("%d:%02d", minutes, remainingSeconds)
        }
    }

    companion object : UiModelMapper<MusicVideo, MusicVideoUiData> {
        override fun fromDomain(domainModel: MusicVideo): MusicVideoUiData {
            return MusicVideoUiData(
                id = domainModel.id,
                title = domainModel.title,
                playtime = domainModel.playtime,
                thumbnail = domainModel.thumbnail
            )
        }
    }
}