package com.wepli.data.playlist.response

import com.wepli.data.song.response.SongResponse
import com.wepli.data.song.response.toSong
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.music.Song
import model.playlist.Playlist
import model.playlist.RecommendPlaylist
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.Date

@Serializable
data class PlaylistResponse(
    @SerialName("playlist_id")
    val id: Int? = null,
    @SerialName("playlist_title")
    val title: String? = null,
    @SerialName("playlist_description")
    val description: String? = null,
    @SerialName("playlist_cover")
    val coverImgUrl: String? = null,
    @SerialName("playlist_author")
    val author: String? = null,
    @SerialName("playlist_created_at")
    val createdAt: String? = null,
    @SerialName("b_side_track")
    val bSideTracks: List<SongResponse>? = emptyList(),
)

fun PlaylistResponse.toPlaylist(): Playlist {
    return Playlist(
        id = id ?: 0,
        title = title.orEmpty(),
        description = description.orEmpty(),
        coverImgUrl = coverImgUrl.orEmpty(),
        author = author.orEmpty(),
        songCnt = bSideTracks?.size ?: 0,
        totalDuration = bSideTracks.orEmpty().sumOf { it.duration ?: 0 }.toLong(),
        bSideTrack = bSideTracks?.map { it.toSong() }.orEmpty(),
        artists = emptyList(),
        createdAt = runCatching { Date(createdAt) }.getOrElse { Date() }
    )
}