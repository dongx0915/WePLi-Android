package com.wepli.data.keyword.repository

import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.data.di.qualifier.SupabaseDataSource
import com.wepli.data.keyword.datasource.KeywordDatasource
import com.wepli.data.keyword.response.toEntityList
import com.wepli.data.network.toEntityResult
import model.recommend.RecommendKeyword
import model.recommend.repository.KeywordRepository
import javax.inject.Inject

class KeywordRepositoryImpl @Inject constructor(
    @SupabaseDataSource private val keywordDatasource: KeywordDatasource
) : KeywordRepository {

    override fun getRecommendKeyword(): FlowResult<List<RecommendKeyword>> {
        return keywordDatasource.getRecommendKeyword().toEntityResult {
            it.toEntityList()
        }
    }
}