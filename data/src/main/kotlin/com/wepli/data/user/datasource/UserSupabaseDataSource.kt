package com.wepli.data.user.datasource

import com.wepli.core.kotlin.flow.FlowResult
import com.wepli.data.user.response.UserResponse

interface UserSupabaseDataSource {

    suspend fun getUserById(id: String): FlowResult<UserResponse>
}