package com.wepli.shared.feature.uimodel.playlist

import com.wepli.common.UiModel
import com.wepli.common.UiModelMapper
import com.wepli.uimodel.artist.ArtistUiData
import com.wepli.uimodel.music.SongUiData
import kotlinx.parcelize.Parcelize
import model.playlist.Playlist
import java.util.Date

@Parcelize
data class PlaylistUiData(
    val id: Int,
    val title: String,
    val description: String,
    val coverImgUrl: String,
    val author: String,
    val bSideTrack: List<SongUiData>,
    val artists: List<ArtistUiData>,
    val createdAt: Date,
): UiModel {

    constructor() : this(0, "", "", "", "", emptyList(), emptyList(), Date())

    companion object : UiModelMapper<Playlist, PlaylistUiData> {
        override fun fromDomain(domainModel: Playlist): PlaylistUiData {
            return PlaylistUiData(
                id = domainModel.id,
                title = domainModel.title,
                description = domainModel.description,
                coverImgUrl = domainModel.coverImgUrl,
                author = domainModel.author,
                bSideTrack = domainModel.bSideTrack.map(SongUiData::fromDomain),
                artists = domainModel.artists.map(ArtistUiData::fromDomain),
                createdAt = domainModel.createdAt
            )
        }
    }
}