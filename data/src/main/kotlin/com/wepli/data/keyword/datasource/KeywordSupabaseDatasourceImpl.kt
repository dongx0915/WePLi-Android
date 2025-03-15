package com.wepli.data.keyword.datasource

import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.data.keyword.response.RecommendKeywordResponse
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class KeywordSupabaseDatasourceImpl @Inject constructor(
    private val supabase: SupabaseClient
) : KeywordDatasource {

    companion object {
        const val RECOMMEND_KEYWORD_VIEW = "recommend_keyword_view"
    }

    override fun getRecommendKeyword(): FlowResult<List<RecommendKeywordResponse>> = flow {
        val result: Result<List<RecommendKeywordResponse>> = runCatching {
            supabase.postgrest[RECOMMEND_KEYWORD_VIEW]
                .select {
                    order("id", Order.ASCENDING)
                }
                .decodeList<RecommendKeywordResponse>()
        }

        emit(result)
    }
}