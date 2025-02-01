package com.wepli.data.song.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.music.Song

@Serializable
data class SongResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("song_id")
    val songId: String, // Apple Music Song Id
    @SerialName("title")
    val title: String,
    @SerialName("artist_name")
    val artist: String,
    @SerialName("album")
    val album: String,
    @SerialName("cover_img")
    val coverImg: String,
    @SerialName("href")
    val href: String,
    @SerialName("duration_millis")
    val duration: Int,
)

fun SongResponse.toSong(): Song {
    return Song(
        id = songId,
        title = title,
        artistName = artist,
        albumName = album,
        coverImg = coverImg,
        href = href,
        durationMillis = duration.toLong(),
        genres = emptyList(),
    )
}