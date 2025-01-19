package com.wepli.data.playlist.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.music.Song
import model.playlist.Playlist
import model.playlist.RecommendPlaylist
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
    @SerialName("song_id")
    val songId: String? = null,
    @SerialName("song_title")
    val songTitle: String? = null,
    @SerialName("song_artist")
    val songArtist: String? = null,
    @SerialName("song_album")
    val songAlbum: String? = null,
    @SerialName("song_cover")
    val songCoverImgUrl: String? = null,
    @SerialName("song_href")
    val songHref: String? = null,
    @SerialName("song_duration")
    val songDurationMillis: Long? = null,
)

fun List<PlaylistResponse>.toPlaylist(): Playlist {
    var durationSum = 0L
    val playlist = this.first()
    val songList = this.map { playlist ->
        durationSum += playlist.songDurationMillis ?: 0L
        Song(
            id = playlist.songId.orEmpty(),
            title = playlist.songTitle.orEmpty(),
            artistName = playlist.songArtist.orEmpty(),
            albumName = playlist.songAlbum.orEmpty(),
            coverImg = playlist.songCoverImgUrl.orEmpty(),
            href = playlist.songHref.orEmpty(),
            genres = emptyList(),
            durationMillis = playlist.songDurationMillis ?: 0L
        )
    }

    return Playlist(
        id = playlist.id ?: 0,
        title = playlist.title.orEmpty(),
        description = playlist.description.orEmpty(),
        coverImgUrl = playlist.coverImgUrl.orEmpty(),
        author = playlist.author.orEmpty(),
        songCnt = songList.size,
        totalDuration = durationSum,
        bSideTrack = songList,
        artists = emptyList(),
        createdAt = runCatching { Date(playlist.createdAt) }.getOrElse { Date() }
    )
}