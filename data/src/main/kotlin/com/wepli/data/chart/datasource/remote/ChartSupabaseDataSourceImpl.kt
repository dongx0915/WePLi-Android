package com.wepli.data.chart.datasource.remote

import com.wepli.data.chart.response.ChartListResponse
import com.wepli.core.kotlin.FlowResult
import com.wepli.data.SupabaseTable
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChartSupabaseDataSourceImpl @Inject constructor(
    private val supabase: SupabaseClient,
) : ChartDataSource {

    override suspend fun getTopChart(): FlowResult<ChartListResponse> = flow {
        val result = runCatching {
            supabase.postgrest[SupabaseTable.CHART_TABLE]
                .select()
                .decodeList<ChartListResponse.ChartResponse>()
                .run(::ChartListResponse) // 기존 API 형식과 동일하게 ChartListResponse로 래핑해서 반환
        }

        emit(result)
    }
}