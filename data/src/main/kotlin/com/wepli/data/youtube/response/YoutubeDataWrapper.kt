package com.wepli.data.youtube.response

data class YoutubeDataWrapper<D>(
    val data: List<D> = emptyList(),
)

