package com.wepli.data.youtube.response

import model.musicvideo.MusicVideo

data class YoutubeVideoResponse(
    val id: VideoId? = null,
    val snippet: VideoSnippet? = null
) {
    // 비디오 ID 정보
    data class VideoId(
        val videoId: String? = null
    )

    // 비디오 상세 정보
    data class VideoSnippet(
        val title: String? = null,
        val description: String? = null,
        val channelTitle: String? = null,
        val publishedAt: String? = null,
        val thumbnails: VideoThumbnails? = null
    )

    // 썸네일 정보
    data class VideoThumbnails(
        val default: Thumbnail? = null,
        val medium: Thumbnail? = null,
        val high: Thumbnail? = null
    ) {
        data class Thumbnail(
            val url: String? = null,
            val width: Int? = null,
            val height: Int? = null
        )
    }
}

fun YoutubeVideoResponse.toDomain(): MusicVideo {
    return MusicVideo(
        id = id?.videoId.orEmpty(),
        title = snippet?.title.orEmpty(),
        playtime = 0f,
        thumbnail = snippet?.thumbnails?.high?.url.orEmpty()
    )
}