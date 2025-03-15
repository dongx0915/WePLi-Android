package com.wepli.data.playlist.datasource.remote

import com.wepli.data.playlist.response.RecommendPlaylistResponse
import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.data.playlist.response.PlaylistResponse

interface PlaylistDataSource {

    fun getRecommendPlaylist(): FlowResult<List<RecommendPlaylistResponse>>

    fun getThemePlaylist(): FlowResult<List<RecommendPlaylistResponse>>

    fun findPlaylistById(playlistId: Int): FlowResult<List<PlaylistResponse>>
}