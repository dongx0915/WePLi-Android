package com.wepli.data.user.datasource

import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.data.SupabaseTable
import com.wepli.data.user.response.UserResponse
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserSupabaseDataSourceImpl @Inject constructor(
    private val supabase: SupabaseClient
) : UserSupabaseDataSource{

    override suspend fun getUserById(id: String): FlowResult<UserResponse> = flow {
        val result = runCatching {
            supabase.postgrest[SupabaseTable.USER_TABLE]
                .select {
                    filter {
                        eq("id", id)
                    }
                }
                .decodeSingle<UserResponse>()
        }

        emit(result)
    }
}