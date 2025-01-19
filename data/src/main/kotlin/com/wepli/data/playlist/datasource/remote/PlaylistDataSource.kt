package com.wepli.data.playlist.datasource.remote

import com.wepli.data.playlist.response.RecommendPlaylistResponse
import com.wepli.core.kotlin.FlowResult

interface PlaylistDataSource {

    fun getRecommendPlaylist(): FlowResult<List<RecommendPlaylistResponse>>

    fun getThemePlaylist(): FlowResult<List<RecommendPlaylistResponse>>
}