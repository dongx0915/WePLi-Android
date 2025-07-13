package com.wepli.data.youtube

import com.wepli.core.common.BuildConfig
import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.data.youtube.response.YoutubeVideoResponse
import com.wepli.data.youtube.response.YoutubeDataWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApi {

    @GET("search")
    fun searchFromQuery(
        @Query("q") searchQuery: String,
        @Query("part") part: String,
        @Query("type") type: String,
        @Query("maxResults") maxResults: Int,
        @Query("key") apiKey: String = BuildConfig.YOUTUBE_API_TOKEN,
    ): FlowResult<YoutubeDataWrapper<YoutubeVideoResponse>>
}