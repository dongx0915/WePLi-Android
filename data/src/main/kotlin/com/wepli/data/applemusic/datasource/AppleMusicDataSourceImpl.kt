package com.wepli.data.applemusic.datasource

import com.wepli.core.kotlin.FlowResult
import com.wepli.data.applemusic.AppleMusicApi
import wepli.domain.search.AppleSearchResponse
import javax.inject.Inject

class AppleMusicDataSourceImpl @Inject constructor(
    private val appleMusicApi: AppleMusicApi,
) : AppleMusicDataSource {

    override fun searchForCatalogResources(
        query: String,
        searchTypes: List<String>,
    ): FlowResult<AppleSearchResponse> {
        return appleMusicApi.searchForCatalogResources(
            term = query,
            types = searchTypes,
        )
    }
}