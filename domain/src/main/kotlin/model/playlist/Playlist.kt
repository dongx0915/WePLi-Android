package model.playlist

import common.DomainModel
import model.artist.Artist
import model.music.Song
import java.util.Date

data class Playlist(
    val id: Int,
    val title: String,
    val description: String,
    val coverImgUrl: String,
    val author: String,
    val bSideTrack: List<Song>,
    val artists: List<Artist>,
    val createdAt: Date,
): DomainModel {
    constructor() : this(0, "", "", "", "", emptyList(), emptyList(), Date())
}