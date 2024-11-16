package com.wepli.data.relaylist.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.relaylist.Relaylist

@Serializable
data class RelaylistResponse(
    @SerialName("title")
    val title: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("imageUrl")
    val coverImgUrl: String?,
    @SerialName("artwork")
    val artwork: Artwork?
) {

    @Serializable
    data class Artwork(
        @SerialName("backgroundColor")
        val backgroundColor: String?,
    )
}

fun List<RelaylistResponse>.toEntities(): List<Relaylist> {
    return map { it.toEntity() }
}

fun RelaylistResponse.toEntity(): Relaylist {
    return Relaylist(
        title = title.orEmpty(),
        description = description.orEmpty(),
        coverImgUrl = coverImgUrl.orEmpty(),
        artwork = artwork?.backgroundColor?.let {
            Relaylist.Artwork(backgroundColor = it.toLong(16))
        }
    )
}
