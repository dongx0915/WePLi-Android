package com.wepli.data.applemusic.datasource

import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.data.applemusic.common.response.AppleAlbumResponse
import com.wepli.data.applemusic.common.response.AppleSongResponse
import com.wepli.data.applemusic.response.AppleCatalogResponse
import com.wepli.data.applemusic.response.AppleSearchResponse

interface AppleMusicDataSource {

    fun searchForCatalogResources(
        query: String,
        searchTypes: List<String>,
        limit: Int,
    ): FlowResult<AppleSearchResponse>

    fun getCatalogCharts(
        chartTypes: List<String>,
    ): FlowResult<AppleCatalogResponse>

    fun getCatalogSong(
        songId: String,
    ): FlowResult<AppleSongResponse>

    fun getCatalogAlbum(
        albumId: String,
    ): FlowResult<AppleAlbumResponse>
}