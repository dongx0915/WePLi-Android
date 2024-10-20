package com.wepli.data.playlist

import com.wepli.data.playlist.response.PlaylistResponse
import com.wepli.core.kotlin.FlowResult
import retrofit2.http.GET

interface PlaylistApi {

    @GET("api/playlists/recommend")
    fun getRecommendPlaylist(): FlowResult<List<PlaylistResponse>>

    @GET("api/playlists/theme")
    fun getThemePlaylist(): FlowResult<List<PlaylistResponse>>
}