package com.wepli.data.relaylist.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.relaylist.Relaylist

@Serializable
data class RelaylistResponse(
    @SerialName("id")
    val id: Int?,
    @SerialName("title")
    val title: String?,
    @SerialName("subscription")
    val description: String?,
    @SerialName("thumbnail")
    val coverImgUrl: String?,
    @SerialName("bg_color")
    val bgColor: String?,
    @SerialName("song_cnt")
    val songCount: Int,
    @SerialName("vote_cnt")
    val voteCount: Int,
    @SerialName("end_date")
    val endDate: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
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
        bSideTrack = emptyList(),
        songCount = songCount,
        voteCount = voteCount,
        endDate = endDate,
        artwork = bgColor?.let {
            Relaylist.Artwork(backgroundColor = it.toLong(16))
        }
    )
}
