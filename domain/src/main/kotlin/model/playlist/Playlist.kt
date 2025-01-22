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
    val songCnt: Int,
    val totalDuration: Long,
    val bSideTrack: List<Song>,
    val artists: List<Artist>,
    val createdAt: Date,
) : DomainModel {
    constructor() : this(0, "", "", "", "", 0, 0L, emptyList(), emptyList(), Date())
}