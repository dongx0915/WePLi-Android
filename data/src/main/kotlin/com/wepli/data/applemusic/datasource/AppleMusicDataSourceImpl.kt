package com.wepli.data.applemusic.datasource

import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.core.kotlin.flow.collectResult
import com.wepli.data.applemusic.AppleMusicApi
import com.wepli.data.applemusic.common.response.AppleAlbumResponse
import com.wepli.data.applemusic.common.response.AppleSongResponse
import com.wepli.data.applemusic.common.response.base.AppleDataWrapper
import com.wepli.data.applemusic.response.AppleCatalogResponse
import com.wepli.data.applemusic.response.AppleSearchResponse
import com.wepli.data.network.toEntityResult
import common.WePLiException
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppleMusicDataSourceImpl @Inject constructor(
    private val appleMusicApi: AppleMusicApi,
) : AppleMusicDataSource {

    override fun searchForCatalogResources(
        query: String,
        searchTypes: List<String>,
        limit: Int,
    ): FlowResult<AppleSearchResponse> {
        return appleMusicApi.searchForCatalogResources(
            term = query,
            types = searchTypes,
            limit = limit
        )
    }

    override fun getCatalogCharts(chartTypes: List<String>): FlowResult<AppleCatalogResponse> {
        return appleMusicApi.searchForCatalogCharts(types = chartTypes)
    }

    override fun getCatalogSong(songId: String): FlowResult<AppleSongResponse> {
        return appleMusicApi.getCatalogSong(songId).map { result ->
            result.map { it.data.first() }
        }
    }

    override fun getCatalogAlbum(albumId: String): FlowResult<AppleAlbumResponse> {
        return appleMusicApi.getCatalogAlbum(albumId).map { result ->
            result.map { it.data.first() }
        }
    }
}