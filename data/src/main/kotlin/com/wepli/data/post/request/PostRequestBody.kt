package com.wepli.data.post.request

import com.wepli.data.song.request.SongRequestBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.community.Post

@Serializable
data class PostRequestBody(
    val title: String,
    val contents: String,
    val author: String,
)

@Serializable
data class PostBsideTrackRequestBody(
    @SerialName("post_id")
    val postId: Int,
    @SerialName("song_id")
    val songId: Int,
)

fun Post.toPostRequest(): PostRequestBody {
    return PostRequestBody(
        title = title,
        contents = content,
        author = author.id,
    )
}

fun Post.mapToSongRequest(): List<SongRequestBody> {
    return songList.map {
        SongRequestBody(
            songId = it.id,
            title = it.title,
            artist = it.artistName,
            album = it.albumName,
            coverImg = it.coverImg,
            href = it.href,
            duration = it.durationMillis.toInt(),
        )
    }
}