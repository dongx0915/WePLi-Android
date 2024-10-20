package com.wepli.data.playlist.datasource.remote

import com.wepli.data.playlist.response.PlaylistResponse
import com.wepli.core.kotlin.FlowResult

interface PlaylistDataSource {

    fun getRecommendPlaylist(): FlowResult<List<PlaylistResponse>>

    fun getThemePlaylist(): FlowResult<List<PlaylistResponse>>
}