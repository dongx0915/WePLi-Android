package com.wepli.data.song.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.music.Song
import org.joda.time.LocalDate

@Serializable
data class SongResponse(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("song_id")
    val songId: String? = null, // Apple Music Song Id
    @SerialName("title")
    val title: String? = null,
    @SerialName("artist_name")
    val artist: String? = null,
    @SerialName("album")
    val album: String? = null,
    @SerialName("cover_img")
    val coverImg: String? = null,
    @SerialName("composerName")
    val composers: String? = null,
    @SerialName("genreNames")
    val genres: List<String>? = null,
    @SerialName("url")
    val url: String? = null,
    @SerialName("previews")
    val previewMusicUrl: List<PreviewResponse>? = null,
    @SerialName("releaseDate")
    val releaseDate: String? = null,
    @SerialName("duration_millis")
    val duration: Int? = null,
    @SerialName("isrc")
    val isrc: String? = null,
    @SerialName("playParams")
    val playParams: PlayParamResponse? = null,
    @SerialName("href")
    val href: String? = null,
) {
    @Serializable
    data class PreviewResponse(
        val url: String? = null
    )

    @Serializable
    data class PlayParamResponse(
        val id: String? = null,
        val kind: String? = null,
    )
}

fun SongResponse.toSong(): Song {
    return Song(
        id = songId.orEmpty(),
        title = title.orEmpty(),
        artistName = artist.orEmpty(),
        albumName = album.orEmpty(),
        coverImg = coverImg.orEmpty(),
        composers = composers?.split(",")?.map { it.trim() }.orEmpty(),
        genres = genres.orEmpty(),
        url = url.orEmpty(),
        previewMusicUrl = previewMusicUrl?.map { it.url.orEmpty() }.orEmpty(),
        releaseDate = LocalDate(releaseDate),
        durationMillis = duration?.toLong() ?: 0L,
        playParams = playParams?.toPlayParams() ?: Song.PlayParams("", ""),
        isrc = isrc.orEmpty(),
        href = href.orEmpty(),
    )
}

fun SongResponse.PlayParamResponse.toPlayParams(): Song.PlayParams {
    return Song.PlayParams(
        id = id.orEmpty(),
        kind = kind.orEmpty(),
    )
}