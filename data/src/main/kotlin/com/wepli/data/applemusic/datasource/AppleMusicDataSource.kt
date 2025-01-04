package com.wepli.data.applemusic.datasource

import com.wepli.core.kotlin.FlowResult
import wepli.domain.search.AppleSearchResponse

interface AppleMusicDataSource {

    fun searchForCatalogResources(
        query: String,
        searchTypes: List<String>,
    ): FlowResult<AppleSearchResponse>
}