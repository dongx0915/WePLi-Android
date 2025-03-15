package com.wepli.data.relaylist.response

import com.wepli.data.song.response.SongResponse
import com.wepli.data.song.response.toSong
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.relaylist.Relaylist
import org.joda.time.DateTime

@Serializable
data class RelaylistResponse(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("subscription")
    val description: String? = null,
    @SerialName("thumbnail")
    val coverImgUrl: String? = null,
    @SerialName("b_side_track")
    val bSideTrack: List<SongResponse>? = null,
    @SerialName("bg_color")
    val bgColor: String? = null,
    @SerialName("song_cnt")
    val songCount: Int? = null,
    @SerialName("vote_cnt")
    val voteCount: Int? = null,
    @SerialName("end_date")
    val endDate: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null,
)

fun List<RelaylistResponse>.toEntities(): List<Relaylist> {
    return map { it.toEntity() }
}

fun RelaylistResponse.toEntity(): Relaylist {
    return Relaylist(
        id = id ?: -1,
        title = title.orEmpty(),
        description = description.orEmpty(),
        coverImgUrl = coverImgUrl.orEmpty(),
        bSideTrack = bSideTrack?.map { it.toSong() }.orEmpty(),
        songCount = songCount ?: 0,
        voteCount = voteCount ?: 0,
        endDate = DateTime.parse(endDate),
        createdAt = DateTime.parse(createdAt),
        artwork = bgColor?.let {
            Relaylist.Artwork(backgroundColor = it.toLong(16))
        }
    )
}
