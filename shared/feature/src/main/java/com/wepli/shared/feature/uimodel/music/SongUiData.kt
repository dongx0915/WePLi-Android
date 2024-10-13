package com.wepli.uimodel.music

import com.wepli.common.UiModel
import com.wepli.common.UiModelMapper
import kotlinx.parcelize.Parcelize
import model.music.Song

/**
 * 음악 정보
 *
 * @property title 제목
 * @property artist 아티스트
 * @property albumCoverImg 앨범 커버 이미지 URL
 */
@Parcelize
data class SongUiData(
    val title: String,
    val artist: String,
    val album: String,
    val albumCoverImg: String,
) : UiModel {

    constructor() : this("", "", "", "")

    companion object : UiModelMapper<Song, SongUiData> {
        override fun fromDomain(domainModel: Song): SongUiData {
            return SongUiData(
                title = domainModel.title,
                artist = domainModel.artist,
                album = domainModel.album,
                albumCoverImg = domainModel.albumCoverImg,
            )
        }
    }
}