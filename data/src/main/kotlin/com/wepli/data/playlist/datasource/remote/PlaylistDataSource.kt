package com.wepli.data.playlist.datasource.remote

import com.wepli.data.playlist.response.PlaylistResponse
import common.FlowResult

interface PlaylistDataSource {

    fun getRecommendPlaylist(): FlowResult<List<PlaylistResponse>>
}