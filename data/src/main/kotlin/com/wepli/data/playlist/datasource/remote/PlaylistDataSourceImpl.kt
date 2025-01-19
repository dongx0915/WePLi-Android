package com.wepli.data.playlist.datasource.remote

import com.wepli.data.playlist.PlaylistApi
import com.wepli.data.playlist.response.RecommendPlaylistResponse
import com.wepli.core.kotlin.FlowResult
import javax.inject.Inject

class PlaylistDataSourceImpl @Inject constructor(
    private val playlistApi: PlaylistApi,
) : PlaylistDataSource {

    override fun getRecommendPlaylist(): FlowResult<List<RecommendPlaylistResponse>> {
        return playlistApi.getRecommendPlaylist()
    }

    override fun getThemePlaylist(): FlowResult<List<RecommendPlaylistResponse>> {
        return playlistApi.getThemePlaylist()
    }
}