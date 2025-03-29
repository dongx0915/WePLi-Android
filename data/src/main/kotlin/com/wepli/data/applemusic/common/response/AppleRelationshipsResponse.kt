package com.wepli.data.applemusic.common.response

import kotlinx.serialization.Serializable

@Serializable
data class AppleRelationshipsResponse(
    val albums: Relationship? = null,
    val artists: Relationship? = null,
) {

    @Serializable
    data class Relationship(
        val href: String? = null,
        val data: List<Data>? = null,
    ) {
        @Serializable
        data class Data(
            val id: String? = null,
            val type: String? = null,
            val href: String? = null,
        )
    }
}