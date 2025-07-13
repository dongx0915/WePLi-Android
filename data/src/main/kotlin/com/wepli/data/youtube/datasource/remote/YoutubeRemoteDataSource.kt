package com.wepli.data.youtube.datasource.remote

import com.wepli.core.common.BuildConfig
import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.data.youtube.response.YoutubeDataWrapper
import com.wepli.data.youtube.response.YoutubeVideoResponse

interface YoutubeRemoteDataSource {

    fun searchFromQuery(
        searchQuery: String,
        part: String,
        type: String,
        maxResults: Int,
        apiKey: String = BuildConfig.YOUTUBE_API_TOKEN,
    ): FlowResult<YoutubeDataWrapper<YoutubeVideoResponse>>
}