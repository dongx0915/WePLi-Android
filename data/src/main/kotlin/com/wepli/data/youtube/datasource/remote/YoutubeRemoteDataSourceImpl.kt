package com.wepli.data.youtube.datasource.remote


import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.data.youtube.YoutubeApi
import com.wepli.data.youtube.response.YoutubeDataWrapper
import com.wepli.data.youtube.response.YoutubeVideoResponse
import javax.inject.Inject

class YoutubeRemoteDataSourceImpl @Inject constructor(
    private val youtubeApi: YoutubeApi
) : YoutubeRemoteDataSource {

    override fun searchFromQuery(
        searchQuery: String,
        part: String,
        type: String,
        maxResults: Int,
        apiKey: String
    ): FlowResult<YoutubeDataWrapper<YoutubeVideoResponse>> {
        return youtubeApi.searchFromQuery(
            searchQuery = searchQuery,
            part = part,
            type = type,
            maxResults = maxResults,
            apiKey = apiKey,
        )
    }
}