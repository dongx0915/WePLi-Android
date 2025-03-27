package com.wepli.data.song.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.music.Song
import org.joda.time.LocalDate

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
    @SerialName("composerName")
    val composers: String,
    @SerialName("genreNames")
    val genres: List<String>,
    @SerialName("url")
    val url: String,
    @SerialName("previews")
    val previewMusicUrl: List<PreviewResponse>,
    @SerialName("releaseDate")
    val releaseDate: String,
    @SerialName("duration_millis")
    val duration: Int,
    @SerialName("playParams")
    val playParams: PlayParamResponse,
    @SerialName("href")
    val href: String,
) {
    @Serializable
    data class PreviewResponse(
        val url: String
    )

    @Serializable
    data class PlayParamResponse(
        val id: String,
        val kind: String,
    )
}

fun SongResponse.toSong(): Song {
    return Song(
        id = songId,
        title = title,
        artistName = artist,
        albumName = album,
        coverImg = coverImg,
        composers = composers.split(",").map { it.trim() },
        genres = genres,
        url = url,
        previewMusicUrl = previewMusicUrl.first().url,
        releaseDate = LocalDate(releaseDate),
        durationMillis = duration.toLong(),
        playParams = playParams.toPlayParams(),
        href = href,
    )
}

fun SongResponse.PlayParamResponse.toPlayParams(): Song.PlayParams {
    return Song.PlayParams(
        id = id,
        kind = kind,
    )
}