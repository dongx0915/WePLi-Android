package com.wepli.data.relaylist.datasource.remote

import com.wepli.core.kotlin.FlowResult
import com.wepli.data.relaylist.response.RelaylistResponse
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RelaylistSupabaseDataSourceImpl @Inject constructor(
    private val supabase: SupabaseClient,
) : RelaylistDataSource {

    companion object {
        const val RELAYLIST_TABLE = "relaylist"
    }

    override fun getRelaylistById(id: Int): FlowResult<RelaylistResponse> = flow {
        val result: Result<RelaylistResponse> = runCatching {
            supabase.postgrest[RELAYLIST_TABLE]
                .select {
                    filter {
                        eq("id", id)
                    }
                }
                .decodeAs<RelaylistResponse>()
        }

        emit(result)
    }

    /**
     * DB에 데이터가 있는데 빈 배열로 응답이 오는 경우
     * 테이블의 RLS(권한) 설정을 확인
     */
    override fun getRelaylists(): FlowResult<List<RelaylistResponse>> = flow {
        val result: Result<List<RelaylistResponse>> = runCatching {
            supabase.postgrest[RELAYLIST_TABLE]
                .select {
                    order("id", Order.ASCENDING)
                }
                .decodeList<RelaylistResponse>()
        }

        emit(result)
    }
}