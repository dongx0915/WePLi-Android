package com.wepli.data.applemusic.datasource

import com.wepli.core.kotlin.FlowResult
import com.wepli.data.applemusic.response.AppleSearchResponse

interface AppleMusicDataSource {

    fun searchForCatalogResources(
        query: String,
        searchTypes: List<String>,
    ): FlowResult<AppleSearchResponse>
}