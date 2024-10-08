package com.wepli.data.playlist

import com.wepli.data.playlist.response.PlaylistResponse
import common.FlowResult
import retrofit2.http.GET

interface PlaylistApi {

    @GET("api/playlists/recommend")
    fun getRecommendPlaylist(): FlowResult<List<PlaylistResponse>>
}