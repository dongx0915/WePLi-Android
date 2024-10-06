package com.wepli.data.chart.response

import kotlinx.serialization.Serializable
import model.music.ChartMusic

@Serializable
data class ChartListResponse(
    val result: List<ChartResponse>
) {
    @Serializable
    data class ChartResponse(
        val rank: Int,
        val title: String,
        val artist: String,
        val album: String,
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