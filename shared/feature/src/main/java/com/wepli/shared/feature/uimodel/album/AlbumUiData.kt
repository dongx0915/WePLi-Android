package com.wepli.shared.feature.uimodel.album

import com.wepli.shared.feature.common.UiModel
import com.wepli.shared.feature.common.UiModelMapper
import com.wepli.uimodel.music.SongUiData
import kotlinx.parcelize.Parcelize
import model.album.Album

@Parcelize
data class AlbumUiData(
    val id: String,
    val href: String,
    val name: String,
    val description: String,
    val coverImg: String,
    val albumUrl: String,
    val isSingle: Boolean,
    val artistId: String,
    val artistName: String,
    val releaseDate: String,
    val copyright: String,
    val trackCount: Int,
    val tracks: List<SongUiData>,
    val genres: List<String>,
) : UiModel {
    constructor() : this("", "", "", "", "", "", false, "", "", "", "", 0, emptyList(), emptyList())

    companion object : UiModelMapper<Album, AlbumUiData> {

        override fun fromDomain(domainModel: Album): AlbumUiData {
            return AlbumUiData(
                id = domainModel.id,
                href = domainModel.href,
                name = domainModel.name,
                description = domainModel.description ?: "",
                coverImg = domainModel.coverImg,
                albumUrl = domainModel.albumUrl,
                isSingle = domainModel.isSingle,
                artistId = domainModel.artistId,
                artistName = domainModel.artistName,
                releaseDate = domainModel.releaseDate,
                copyright = domainModel.copyright,
                trackCount = domainModel.trackCount,
                tracks = domainModel.tracks.map { SongUiData.fromDomain(it) },
                genres = domainModel.genres
            )
        }
    }
}