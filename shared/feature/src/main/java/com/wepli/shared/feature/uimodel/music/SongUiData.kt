package com.wepli.uimodel.music

import android.os.Parcelable
import com.wepli.shared.feature.common.UiModel
import com.wepli.shared.feature.common.UiModelMapper
import kotlinx.parcelize.Parcelize
import model.music.Song
import model.music.Song.PlayParams
import org.joda.time.LocalDate

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
    val composers: List<String> = emptyList(),
    val genres: List<String> = emptyList(),
    val url: String = "",
    val previewMusicUrl: List<String> = emptyList(),
    val releaseDate: LocalDate = LocalDate.now(),
    val durationMillis: Long = 0L,
    val playParams: PlayParamsUiData = PlayParamsUiData(),
    val isrc: String = "",
    val href: String = "",
    val isSelected: Boolean = false,
) : UiModel {

    @Parcelize
    data class PlayParamsUiData(
        val id: String = "",
        val kind: String = "",
    ) : UiModel

    override fun equals(other: Any?): Boolean {
        return other is SongUiData && id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun getImageUrl(size: Int = 500): String {
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
                durationMillis = domainModel.durationMillis,
                composers = domainModel.composers,
                url = domainModel.url,
                previewMusicUrl = domainModel.previewMusicUrl,
                releaseDate = domainModel.releaseDate,
                playParams = PlayParamsUiData(
                    id = domainModel.playParams.id,
                    kind = domainModel.playParams.kind
                )
            )
        }
    }
}

fun SongUiData.toDomain(): Song {
    return Song(
        id = id,
        title = title,
        artistName = artistName,
        albumName = albumName,
        coverImg = coverImg,
        href = href,
        genres = genres,
        durationMillis = durationMillis,
        composers = composers,
        url = url,
        previewMusicUrl = previewMusicUrl,
        releaseDate = releaseDate,
        isrc = isrc,
        playParams = PlayParams(
            id = playParams.id,
            kind = playParams.kind
        )
    )
}