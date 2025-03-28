package com.wepli.data.song.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SongRequestBody(
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
    @SerialName("composers")
    val composers: List<String>,
    @SerialName("genres")
    val genres: List<String>,
    @SerialName("url")
    val url: String,
    @SerialName("preview_music_url")
    val previewMusicUrl: String,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("duration_millis")
    val duration: Int,
    @SerialName("isrc")
    val isrc: String,
    @SerialName("href")
    val href: String,
)