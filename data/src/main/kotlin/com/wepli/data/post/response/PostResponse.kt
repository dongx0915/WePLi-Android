package com.wepli.data.post.response

import com.wepli.data.song.response.SongResponse
import com.wepli.data.song.response.toSong
import com.wepli.data.user.response.UserResponse
import com.wepli.data.user.response.toUser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.community.Post

@Serializable
data class PostResponse(
    @SerialName("post_id")
    val id: Int,
    @SerialName("post_title")
    val title: String,
    @SerialName("post_contents")
    val contents: String,
    @SerialName("user")
    val author: UserResponse,
    @SerialName("song_list")
    val songList: List<SongResponse>,
)

fun List<PostResponse>.toPosts(): List<Post> {
    return map { it.toPost() }
}

fun PostResponse.toPost(): Post {
    return Post(
        id = id,
        title = title,
        content = contents,
        author = author.toUser(),
        songList = songList.map { it.toSong() },
    )
}