package com.wepli.data.keyword.datasource

import android.util.Log
import com.wepli.core.kotlin.FlowResult
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

    override fun getRecommendKeyword(): FlowResult<RecommendKeywordResponse> = flow {
        val result: Result<RecommendKeywordResponse> = runCatching {
            supabase.postgrest[RECOMMEND_KEYWORD_VIEW]
                .select {
                    order("id", Order.ASCENDING)
                }
                .decodeAs<RecommendKeywordResponse>()
        }.onSuccess {
            Log.d("KeywordSupabaseDatasourceImpl", "getRecommendKeyword: $it")
        }.onFailure {
            Log.d("KeywordSupabaseDatasourceImpl", "getRecommendKeyword: $it")
        }

        emit(result)
    }
}