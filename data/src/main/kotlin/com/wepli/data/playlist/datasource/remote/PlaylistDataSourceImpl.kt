package com.wepli.data.playlist.datasource.remote

import com.wepli.data.playlist.PlaylistApi
import com.wepli.data.playlist.response.PlaylistResponse
import common.FlowResult
import javax.inject.Inject

class PlaylistDataSourceImpl @Inject constructor(
    private val playlistApi: PlaylistApi,
) : PlaylistDataSource {

    override fun getRecommendPlaylist(): FlowResult<List<PlaylistResponse>> {
        return playlistApi.getRecommendPlaylist()
    }

    override fun getThemePlaylist(): FlowResult<List<PlaylistResponse>> {
        return playlistApi.getThemePlaylist()
    }
}