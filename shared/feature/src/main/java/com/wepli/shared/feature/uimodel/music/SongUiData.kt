package com.wepli.uimodel.music

import com.wepli.common.UiModel
import com.wepli.common.UiModelMapper
import kotlinx.parcelize.Parcelize
import model.music.Song

/**
 * 음악 정보
 *
 * @property title 제목
 * @property artistName 아티스트
 * @property coverImg 앨범 커버 이미지 URL
 */
@Parcelize
data class SongUiData(
    val id: String = "",
    val title: String = "",
    val artistName: String = "",
    val albumName: String = "",
    val coverImg: String = "",
    val href: String = "",
    val genres: List<String> = emptyList(),
    val durationMillis: Long = 0L,
) : UiModel {

    fun getImageUrl(size: Int): String {
        return getImageUrl(size, size)
    }

    fun getImageUrl(width: Int, height: Int): String {
        return coverImg.replace("{w}", width.toString()).replace("{h}", height.toString())
    }

    companion object : UiModelMapper<Song, SongUiData> {
        override fun fromDomain(domainModel: Song): SongUiData {
            return SongUiData(
                id = domainModel.id,
                title = domainModel.title,
                artistName = domainModel.artistName,
                albumName = domainModel.albumName,
                coverImg = domainModel.coverImg,
                href = domainModel.href,
                genres = domainModel.genres,
                durationMillis = domainModel.durationMillis
            )
        }
    }
}