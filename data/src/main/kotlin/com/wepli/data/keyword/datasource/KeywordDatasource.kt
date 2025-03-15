package com.wepli.data.keyword.datasource

import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.data.keyword.response.RecommendKeywordResponse

interface KeywordDatasource {

    fun getRecommendKeyword(): FlowResult<List<RecommendKeywordResponse>>
}