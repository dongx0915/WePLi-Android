package com.wepli.data.youtube.repository

import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.data.network.toEntityResult
import com.wepli.data.youtube.datasource.remote.YoutubeRemoteDataSource
import com.wepli.data.youtube.response.toDomain
import model.musicvideo.MusicVideo
import javax.inject.Inject

class YoutubeRepositoryImpl @Inject constructor(
    private val youtubeRemoteDataSource: YoutubeRemoteDataSource
): YoutubeRepository {

    override fun searchMusicVideo(searchQuery: String): FlowResult<MusicVideo> {
        return youtubeRemoteDataSource.searchFromQuery(
            searchQuery = searchQuery,
            part = "snippet",
            type = "video",
            maxResults = 1,
        ).toEntityResult {
            it.data.first().toDomain()
        }
    }
}