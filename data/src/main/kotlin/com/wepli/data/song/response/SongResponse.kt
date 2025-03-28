package com.wepli.data.song.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.music.Song
import org.joda.time.LocalDate

@Serializable
data class SongResponse(
    @SerialName("id")
    val id: Int?,
    @SerialName("song_id")
    val songId: String?, // Apple Music Song Id
    @SerialName("title")
    val title: String?,
    @SerialName("artist_name")
    val artist: String?,
    @SerialName("album")
    val album: String?,
    @SerialName("cover_img")
    val coverImg: String?,
    @SerialName("composerName")
    val composers: String?,
    @SerialName("genreNames")
    val genres: List<String>?,
    @SerialName("url")
    val url: String?,
    @SerialName("previews")
    val previewMusicUrl: List<PreviewResponse>?,
    @SerialName("releaseDate")
    val releaseDate: String?,
    @SerialName("duration_millis")
    val duration: Int?,
    @SerialName("playParams")
    val playParams: PlayParamResponse?,
    @SerialName("href")
    val href: String?,
) {
    @Serializable
    data class PreviewResponse(
        val url: String?
    )

    @Serializable
    data class PlayParamResponse(
        val id: String?,
        val kind: String?,
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
        href = href.orEmpty(),
    )
}

fun SongResponse.PlayParamResponse.toPlayParams(): Song.PlayParams {
    return Song.PlayParams(
        id = id.orEmpty(),
        kind = kind.orEmpty(),
    )
}