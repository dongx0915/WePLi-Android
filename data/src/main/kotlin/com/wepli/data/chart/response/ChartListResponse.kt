package com.wepli.data.chart.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.music.ChartMusic

@Serializable
data class ChartListResponse(
    val result: List<ChartResponse>
) {
    @Serializable
    data class ChartResponse(
        @SerialName("rank")
        val rank: Int,
        @SerialName("title")
        val title: String,
        @SerialName("artist")
        val artist: String,
        @SerialName("album")
        val album: String,
        @SerialName("album_cover_url")
        val albumCoverUrl: String
    )
}

fun ChartListResponse.toEntities(): List<ChartMusic> {
    return result.map {
        ChartMusic(
            rank = it.rank,
            title = it.title,
            artist = it.artist,
            album = it.album,
            albumCoverUrl = it.albumCoverUrl
        )
    }
}