package com.wepli.data.playlist

import com.wepli.data.playlist.response.RecommendPlaylistResponse
import com.wepli.core.kotlin.flow.FlowResult
import retrofit2.http.GET

interface PlaylistApi {

    @GET("api/playlists/recommend")
    fun getRecommendPlaylist(): FlowResult<List<RecommendPlaylistResponse>>

    @GET("api/playlists/theme")
    fun getThemePlaylist(): FlowResult<List<RecommendPlaylistResponse>>
}