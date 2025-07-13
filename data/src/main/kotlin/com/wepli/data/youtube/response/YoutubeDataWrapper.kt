package com.wepli.data.youtube.response

import kotlinx.serialization.Serializable

@Serializable
data class YoutubeDataWrapper<D>(
    val items: List<D> = emptyList(),
)