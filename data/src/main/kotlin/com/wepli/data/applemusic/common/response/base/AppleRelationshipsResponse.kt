package com.wepli.data.applemusic.common.response.base

import com.wepli.data.applemusic.common.response.AppleSongResponse
import kotlinx.serialization.Serializable

@Serializable
data class AppleRelationshipsResponse(
    val albums: Relationship<Data>? = null,
    val artists: Relationship<Data>? = null,
    val tracks: Relationship<SongData>? = null,
) {

    @Serializable
    data class Relationship<T : Data>(
        val href: String? = null,
        val data: List<T>? = null,
    )

    @Serializable
    open class Data(
        val id: String? = null,
        val type: String? = null,
        val href: String? = null,
    )

    @Serializable
    data class SongData(
        val attributes: AppleSongResponse.Attributes? = null,
    ) : Data()
}